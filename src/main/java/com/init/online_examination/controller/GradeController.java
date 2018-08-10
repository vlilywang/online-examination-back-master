package com.init.online_examination.controller;

import com.init.online_examination.domain.ExamPaper;
import com.init.online_examination.domain.Grade;
import com.init.online_examination.domain.User;
import com.init.online_examination.service.ExamPaperService;
import com.init.online_examination.service.GradeService;
import com.init.online_examination.service.UserService;
import com.init.online_examination.utilty.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/grade")
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

    // 获取所有 不加分页
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Grade> findAll() {
        return gradeService.findAll();
    }
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
    @RequestMapping(value = "/current/all", method = RequestMethod.GET)
    public List<Grade> getCurrentUserAllGrade() {
        User user = userService.current();
        return gradeService.findCurrentAll(user);
    }
    // 根据试卷查看 不分页
    @RequestMapping(value = "/paper/{id}", method = RequestMethod.GET)
    public List<Grade> getAllByPaperId(@PathVariable Long id) {
        ExamPaper examPaper = examPaperService.get(id);
        return gradeService.findPaperAll(examPaper);
    }
    // 结束考试
    @RequestMapping(value = "/end/{examId}", method = RequestMethod.POST)
    public ResponseEntity endExam(@PathVariable Long examId, @RequestBody List<Map> body) {
        ExamPaper examPaper = examPaperService.get(examId);
        if (examPaper == null) {
            return ResultData.error("该试卷不存在");
        }
        User user = userService.current();
        if (user == null) {
            return ResultData.error("先登录");
        }
//        String[] stuAnswer = null;
//        List<Long> questIds = null;
//        List<String[]> answers = null;
//        for (int i = 0; i < body.size(); i++){
//            Long questId = Long.valueOf(body.get(i).get("questId").toString());
//            String[] answer = body.get(i).get("answer").toString().split(",");
//            questIds.add(questId);
//            answers.add(answer);
//        }
//        Long questId = Long.valueOf(body.get(questId).toString());
//        if (body.containsKey("answer") && !body.get("answer").toString().isEmpty()) {
//            String getAnswer =  body.get("answer").toString();
//            stuAnswer = getAnswer.split(",");
//            System.out.println("stuAnswer:::::" + stuAnswer.toString());
//        } else {
//            return ResultData.error("缺少答案");
//        }
        try {
            return ResultData.success(gradeService.add(examPaper, user, body));
        } catch (Exception e) {
            return ResultData.error(e.getMessage());
        }
    }
}
