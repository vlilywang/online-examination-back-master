package com.init.online_examination.service;

import com.init.online_examination.domain.Role;
import com.init.online_examination.domain.RoleRepository;
import com.init.online_examination.domain.User;
import org.springframework.data.domain.PageRequest;
import com.init.online_examination.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
//import org.springframework.data.jpa.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findFirstByUsernameAndIsDeleted(s, 0);
    }
    // 搜索
    public Page<User> find(Date beginTime, Date endTime, String[] keyword, Role role, Integer page, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (keyword != null && keyword.length != 0) {
                    predicateList.add(criteriaBuilder.isNotNull(root.get("keyword")));
                    predicateList.add(criteriaBuilder.like(root.get("keyword").as(String.class), "%" + keyword + "%"));
                }
                if (role != null) {
                    predicateList.add(criteriaBuilder.isNotNull(root.get("role")));
                    predicateList.add(criteriaBuilder.equal(root.get("role").as(Role.class), role));
                }
                System.out.println("beginTime:" + beginTime);
                System.out.println("endTime:" + endTime);
                if (beginTime != null || endTime != null) {
                    predicateList.add(criteriaBuilder.isNotNull(root.get("createTime")));
                }
                if (beginTime != null) {
                    predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("beginTime").as(Date.class), beginTime));
                }
                if (endTime != null) {
                    predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("endTime").as(Date.class), endTime));
                }
                Predicate[] arrayType = new Predicate[predicateList.size()];
                return criteriaBuilder.and(predicateList.toArray(arrayType));
            }
        };
        return userRepository.findAll(specification, PageRequest.of(page - 1, pageSize, sort));
    }
    // 获取当前用户
    public User current() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (object instanceof User) {
            return (User) object;
        } else {
            return null;
        }
    }

    // 所有用户列表 不分页
    public List<User> list() {
        return userRepository.findAll();
    }

    // 根据id获取仍有效的用户
    public User get(Long id) {
        return userRepository.findFirstByIdAndIsDeleted(id, 0);
    }

    // 密码
    public User updatePassword(User user, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (password != null) {
            user.setPassword(encoder.encode(password));
        }
        userRepository.save(user);
        return user;
    }
    // 新建用户
    public User create(String name, String username, String password, Role role) throws Exception {
        User user = userRepository.findFirstByUsernameAndIsDeleted(username, 0);
        if (user != null) {
            throw new Exception("已存在相同用户名的用户");
        }
        Date current = new Date();
        user = new User();
        user.setName(name);
        user.setUsername(username);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(password));
        user.setRole(role);
        user.setIsDeleted(0);
        user.setCreateTime(current);
        userRepository.save(user);
        return user;
    }
    // 删除用户
    public void delete(User user) {
        user.setIsDeleted(1);
        userRepository.save(user);
    }

    // 修改用户
    public User update(User user, String name, Role role) throws Exception {
        if (name != null && !name.isEmpty()) {
            user.setName(name);
        }
        if (role != null) {
            user.setRole(role);
        }
        userRepository.save(user);
        return user;
    }
    public Role getRole(Long id ){
        return roleRepository.findFirstById(id);
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    // runner
    public Role createRole(String name) {
        Role role = new Role();
        role.setName(name);
        roleRepository.save(role);
        return role;
    }

    // runner
    public Long countRole() {
        return roleRepository.count();
    }

    // runner
    public Long count() {
        return userRepository.count();
    }

}
