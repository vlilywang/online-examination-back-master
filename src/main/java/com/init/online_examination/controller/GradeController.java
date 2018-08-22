package com.init.online_examination.controller;

import com.init.online_examination.domain.ExamPaper;
import com.init.online_examination.domain.Grade;
import com.init.online_examination.domain.User;
import com.init.online_examination.service.ExamPaperService;
import com.init.online_examination.service.GradeService;
import com.init.online_examination.service.UserService;
import com.init.online_examination.utilty.PageData;
import com.init.online_examination.utilty.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/grade")
// 提交试卷时没加答案个数的判断
// 提交试卷时 前端传的试题的id
public class GradeController {
    private GradeService gradeService;
    private UserService userService;
    private ExamPaperService examPaperService;


    @Autowired
    public void setExamPaperService(ExamPaperService examPaperService) {
        this.examPaperService = examPaperService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity find(@RequestParam(defaultValue = "") Date beginTime,
                               @RequestParam(defaultValue = "") Date endTime,
                               @RequestParam(defaultValue = "") Long userId,
                               @RequestParam(defaultValue = "") Long exampaperId,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        User user = null;
        if (userId != null) {
            user = userService.get(userId);
            if (user == null) {
                return ResultData.error("该用户不存在");
            }
        }
        ExamPaper examPaper = null;
        if (exampaperId != null) {
            examPaper = examPaperService.get(exampaperId);
            if (examPaper == null) {
                return ResultData.error("该试卷不存在");
            }
        }
//        Long count = gradeService.count();
        Page<Grade> grades = gradeService.find(beginTime, endTime, user, examPaper, page, pageSize);
        return ResultData.success(new PageData(grades, page, pageSize));
    }


    // 获取所有 不加分页
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public List<Grade> findAll() {
//        return gradeService.findAll();
//    }
    // 根据试卷id获取详情
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable Long id) {
        Grade grade = gradeService.findGradeById(id);
        if (grade != null) {
            return ResultData.success(grade);
        } else {
            return ResultData.error("考试不存在");
        }
    }

    // 根据当前用户参加的所有考试的详情 不分页
    @RequestMapping(value = "/current/all/noPage", method = RequestMethod.GET)
    public List<Grade> getCurrentUserAllGrade() {
        User user = userService.current();
        return gradeService.findCurrentAll(user);
    }

    // 当前用户参加的所有考试的详情 分页
    @RequestMapping(value = "/current/all", method = RequestMethod.GET)
    public ResponseEntity findCurrentAll(@RequestParam(defaultValue = "") Date beginTime,
                                         @RequestParam(defaultValue = "") Date endTime,
                                         @RequestParam(defaultValue = "") Long exampaperId,
                                         @RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        ExamPaper examPaper = null;
        if (exampaperId != null) {
            examPaper = examPaperService.get(exampaperId);
            if (examPaper == null) {
                return ResultData.error("该试卷不存在");
            }
        }
        User user = userService.current();
        if (user == null) {
            return ResultData.error("请先登录");
        }
//        Long count = gradeService.countByUser(user);
        Page<Grade> grades = gradeService.find(beginTime, endTime, user, examPaper, page, pageSize);
        return ResultData.success(new PageData(grades, page, pageSize));
    }

    // 根据试卷查看 不分页
    @RequestMapping(value = "/paper/{id}/noPage", method = RequestMethod.GET)
    public List<Grade> getAllByPaperId(@PathVariable Long id) {
        ExamPaper examPaper = examPaperService.get(id);
        return gradeService.findPaperAll(examPaper);
    }

    // 根据试卷id查看所有成绩 分页
//    @RequestMapping(value = "/paper/{id}", method = RequestMethod.GET)
//    public ResponseEntity findByExampaper(@PathVariable Long id,
//                                          @RequestParam(defaultValue = "") Date beginTime,
//                                         @RequestParam(defaultValue = "") Date endTime,
//                                         @RequestParam(defaultValue = "1") Integer page,
//                                         @RequestParam(defaultValue = "20") Integer pageSize) {
//        ExamPaper examPaper = examPaperService.get(id);
//
//        User user = userService.current();
//        if (user == null) {
//            return ResultData.error("请先登录");
//        }
//        Long count = gradeService.countByUser(user);
//        Page<Grade> grades = gradeService.find(beginTime, endTime, user, examPaper, page, pageSize);
//        return ResultData.success(new PageData(grades, page, pageSize, count));
//    }
    // 结束考试
    @RequestMapping(value = "/end/{examId}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity endExam(@PathVariable Long examId, @RequestBody List<Map> body) {
        ExamPaper examPaper = examPaperService.get(examId);
        if (examPaper == null) {
            return ResultData.error("该试卷不存在");
        }
        User user = userService.current();
        if (user == null) {
            return ResultData.error("先登录");
        }
        List<Grade> grades = this.gradeService.findAllByUserAndExamPaper(user, examPaper);
        if (grades.size() == 0) {
            try {
                return ResultData.success(gradeService.add(examPaper, user, body));
            } catch (Exception e) {
                return ResultData.error(e.getMessage());
            }
        } else {
            return ResultData.error("你已经考过该试卷了");
        }
    }
}
