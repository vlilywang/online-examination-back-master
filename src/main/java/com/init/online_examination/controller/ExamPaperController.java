package com.init.online_examination.controller;

import com.init.online_examination.domain.ExamPaper;
import com.init.online_examination.domain.Type;
import com.init.online_examination.service.ExamPaperService;
import com.init.online_examination.service.QuestionService;
import com.init.online_examination.utilty.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api/exampaper")
public class ExamPaperController {
    private ExamPaperService examPaperService;
    private QuestionService questionService;

    @Autowired
    public void setExamPaperService(ExamPaperService examPaperService) {
        this.examPaperService = examPaperService;
    }
    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    // 获取所有试卷 不分页
    @RequestMapping(name = "/noPage", method = RequestMethod.GET)
    public ResponseEntity all() {
        return ResultData.success(examPaperService.list());
    }

    // 获取试卷列表 带分页
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public ResponseEntity find(@RequestParam(defaultValue = "") Date beginTime,
//                               @RequestParam(defaultValue = "") Date endTime,
//                               @RequestParam(defaultValue = "") String[] keyword,
//                               @RequestParam(defaultValue = "1") Integer page,
//                               @RequestParam(defaultValue = "20") Integer pageSize) {
//        Page<ExamPaper> examPapers = examPaperService.find(beginTime, endTime, keyword, page, pageSize);
//        return ResultData.success(examPapers);
//    }

    // 根据id获取试卷详情 题目 答案
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable Long id) {
        ExamPaper examPaper = examPaperService.get(id);
        if (examPaper != null) {
            return ResultData.success(examPaper);
        } else {
            return ResultData.error("该试卷不存在");
        }
    }

    // 新建试卷
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity create(@RequestBody Map body) {
        String title = "";
        String keyword = "";
        Integer singleAmount = 0;
        Integer multiAmount = 0;
        Integer judgeAmount = 0;
        /**
         * private Long id;
         *     private String title;
         *     private Integer Duration; // 持续时间，固定值 60 分钟
         *     private Integer singleAmount;
         *     private Integer multiAmount;
         *     private Integer judgeAmount;
         *     private Date createTime;
         *     private String keyword;
         *     private Integer isDeleted;*/

        if (body.containsKey("title") && !body.get("title").toString().isEmpty()) {
            title = body.get("title").toString().trim();
        } else {
            return ResultData.error("缺少试卷标题");
        }
        if (body.containsKey("keyword") && !body.get("keyword").toString().isEmpty()) {
            keyword = body.get("keyword").toString();
        } else {
            return ResultData.error("缺少关键词");
        }

        if (body.containsKey("singleAmount") && !body.get("singleAmount").toString().isEmpty()) {
            singleAmount = Integer.valueOf(body.get("singleAmount").toString());
        } else {
            singleAmount = 0;
        }
        if (body.containsKey("multiAmount") && !body.get("multiAmount").toString().isEmpty()) {
            multiAmount = Integer.valueOf(body.get("multiAmount").toString());
        } else {
            multiAmount = 0;
        }
        if (body.containsKey("judgeAmount") && !body.get("judgeAmount").toString().isEmpty()) {
            judgeAmount = Integer.valueOf(body.get("judgeAmount").toString());
        } else {
            judgeAmount = 0;
        }

        // 注意：输入的数目应判断比数据库里的试题每类的数目少
        // 随机选题目
        Type typeSingle = questionService.findById(1L);
        Type typeMulti = questionService.findById(2L);
        Type typeJudge = questionService.findById(3L);
        Integer singleCount = questionService.countByTypeAndIsDeleted(typeSingle, 0);
        Integer multiCount = questionService.countByTypeAndIsDeleted(typeMulti, 0);
        Integer judgeCount = questionService.countByTypeAndIsDeleted(typeJudge, 0);

        if (singleAmount > singleCount) {
            return ResultData.error("单选题不够，只有" + singleCount + "个");
        }
        if (multiAmount > multiCount) {
            return ResultData.error("多选题不够，只有" + multiCount + "个");
        }
        if (judgeAmount > judgeCount) {
            return ResultData.error("判断题不够，只有" + judgeCount + "个");
        }

//        if (singleAmount * 1 + multiAmount * 2 + judgeAmount * 0.5 != 100) {
//            return ResultData.error("试卷总分应该为100分，有问题昂");
//        }

        try {
            ExamPaper examPaper = examPaperService.create(title, keyword, singleAmount, multiAmount, judgeAmount);
            return ResultData.success(examPaper);
        } catch (Exception ex) {
            return ResultData.error(ex.getMessage());
        }
    }

    // 删除试卷
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id) {
        ExamPaper examPaper = examPaperService.get(id);
        if (examPaper == null) {
            return ResultData.error("id参数不正确");
        }
        examPaperService.delete(examPaper);
        return ResultData.success();
    }
}
