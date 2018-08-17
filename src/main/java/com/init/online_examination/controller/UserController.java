package com.init.online_examination.controller;

import com.init.online_examination.domain.Role;
import com.init.online_examination.domain.User;
import com.init.online_examination.service.UserService;
import com.init.online_examination.utilty.PageData;
import com.init.online_examination.utilty.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/user")
// 搜索用户接口 关键词未实现
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // 查询 isDeleted == 0
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity find(@RequestParam(defaultValue = "") Date beginTime,
                               @RequestParam(defaultValue = "") Date endTime,
                               @RequestParam(defaultValue = "") String keyword,
                               @RequestParam(defaultValue = "0") Long roleId,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        Role role = null;
        if (roleId != 0) {
            role = userService.getRole(roleId);
            if (role == null) {
                return ResultData.error("指定的角色id不正确");
            }
        }
        Page<User> users = userService.find(beginTime, endTime, keyword, role, page, pageSize);
        return ResultData.success(new PageData(users, page, pageSize));
    }
    // 获取当前用户信息
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public ResponseEntity current() {
        return ResultData.success(userService.current());
    }

//    // 获取当前用户参加的考试和分数 // 不分页
//    @RequestMapping(value = "/currentgrade", method = RequestMethod.GET)
//    public ResponseEntity currentGrade() {
//        User user = userService.current();
//        user.getGrade();
//        return ResultData.success(userService.current());
//    }


    // 新建用户
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity create(@RequestBody Map body) {
        String name = "";
        String username = null;
        String password = null;
        Role role = null;
        if (body.containsKey("name")) {
            name = body.get("name").toString();
        }
        if (body.containsKey("username") && !body.get("username").toString().isEmpty()) {
            username = body.get("username").toString().trim();
        } else {
            return ResultData.error("缺少用户名参数");
        }
        password = "123456";
        if (body.containsKey("roleId")) {
            role = userService.getRole(Long.valueOf(body.get("roleId").toString()));
            if (role == null) {
                return ResultData.error("roleId参数不正确");
            }
        } else {
            return ResultData.error("缺少roleId参数");
        }
        try {
            User user = userService.create(name, username, password, role);
            return ResultData.success(user);
        } catch (Exception ex) {
            return ResultData.error(ex.getMessage());
        }
    }

// 修改密码
    @RequestMapping(value = "/{id}/password", method = RequestMethod.PUT)
    public ResponseEntity updatePassword(@PathVariable Long id,  @RequestBody Map body) {
        User user = userService.get(id);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (user == null) {
            return ResultData.error("id输入有误");
        }
        String newPassword = body.get("newPassword").toString();
        try {
            return ResultData.success(userService.updatePassword(user, newPassword));
        } catch (Exception e) {
            return ResultData.error(e.getMessage());
        }
    }

    // 修改当前用户密码
    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    public ResponseEntity updateCurrentPassword(@RequestBody Map body) {
        User user = userService.current();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (user == null) {
            return ResultData.error("请先登录");
        }
        String dataPassword = user.getPassword();
        String oldPassword = body.get("oldPassword").toString();
        String newPassword = body.get("newPassword").toString();
        if (encoder.matches(oldPassword, dataPassword)) {
            if (encoder.matches(oldPassword, newPassword)) {
                return ResultData.error("请输入与旧密码不同的新密码");
            } else {
                try {
                    return ResultData.success(userService.updatePassword(user, newPassword));
                } catch (Exception e) {
                    return ResultData.error(e.getMessage());
                }
            }
        } else {
            return ResultData.error("旧密码输入有误");
        }
    }
//    @RequestMapping(value = "", method = RequestMethod.PUT)
    // 根据id获取用户详情 isDeleted == 0
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable Long id) {
        User user = userService.get(id);
        if (user != null) {
            return ResultData.success(user);
        } else {
            return ResultData.error("用户信息不存在");
        }
    }

    // 修改用户 isDeleted == 0
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Map body) throws Exception {
        String name = null;
        Role role = null;
        User user = userService.get(id);
        if (user == null) {
            return ResultData.error("该用户不存在");
        }
        if (body.containsKey("name")) {
            name = body.get("name").toString();
        }
        if (body.containsKey("roleId")) {
            role = userService.getRole(Long.valueOf(body.get("roleId").toString()));
            if (role == null) {
                return ResultData.error("roleId参数不正确");
            }
        }
        return ResultData.success(userService.update(user, name, role));
    }
    // 考完试后修改用户is_used状态
    // 修改用户 isDeleted == 0
    @RequestMapping(value = "/update/isUsed/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity updateIsUsed(@PathVariable Long id) throws Exception {
        User user = userService.get(id);
        if (user == null) {
            return ResultData.error("该用户不存在");
        }
        return ResultData.success(userService.update(user));
    }

    // 删除用户 isDeleted == 0
    @RequestMapping(value = "/delete/id/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity delete(@PathVariable Long id) {
        User user = userService.get(id);
        if (user == null) {
            return ResultData.error("id参数不正确");
        }
        if (user.getIsUsed().equals(0)) {
            userService.delete(user);
        } else {
            return ResultData.error("该用户已经考过试，不能删除");
        }

        return ResultData.success();
    }

    // 批量删除用户 isDeleted == 0
    @RequestMapping(value = "/delete/ids/{ids}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity deleteIds(@PathVariable List<Long> ids) {
          List<User> users = new ArrayList<>();
        for (Integer i = 0; i < ids.size(); i++) {
            User user = userService.get(ids.get(i));
            if (user != null && user.getIsUsed().equals(0)) {
                users.add(user);
            } else {
                return ResultData.error("用户不存在/用户考过试，不能删");
            }
        }
        try{
            for (Integer i = 0; i< users.size(); i++) {
                userService.delete(users.get(i));
            }
            return ResultData.success();
        } catch (Exception e) {
            return ResultData.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public ResponseEntity listRole() {
        return ResultData.success(userService.getRoles());
    }

    @RequestMapping(value = "/roles/{id}", method = RequestMethod.GET)
    public ResponseEntity getRole(@PathVariable Long id) {
        return ResultData.success(userService.get(id));
    }
}
