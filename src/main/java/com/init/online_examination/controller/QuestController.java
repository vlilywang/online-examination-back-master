package com.init.online_examination.controller;

import com.init.online_examination.domain.*;
import com.init.online_examination.service.QuestionService;
import com.init.online_examination.utilty.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/question")
public class QuestController {
    private QuestionService questionService;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    // 获取试题列表 带分页
    @RequestMapping(value = "", method = RequestMethod.GET)
        public ResponseEntity find(@RequestParam(defaultValue = "") Date beginTime,
                                   @RequestParam(defaultValue = "") Date endTime,
                               @RequestParam(defaultValue = "") String[] keyword,
                               @RequestParam(defaultValue = "0") Long typeId,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "20") Integer pageSize) {
        Type type = null;
        if (typeId != 0) {
            type = questionService.findById(typeId);
            if (type == null) {
                return ResultData.error("指定的试题类型id不正确");
            }
        }
        Page<Question> questions = questionService.find(beginTime, endTime, keyword, type, page, pageSize);
        return ResultData.success(questions);
    }

    // 获取试题列表 不分页
    @RequestMapping(value = "/noPage", method = RequestMethod.GET)
    public ResponseEntity list() {
        return ResultData.success(questionService.list());
    }

    // 新建试题
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity create(@RequestBody Map body) {
        String title = "";
        String[] answer = null;
        String[] option = null;
        Type type = null;
        String keyword = "";
        if (body.containsKey("title") && !body.get("title").toString().isEmpty()) {
            title = body.get("title").toString().trim();
        } else {
            return ResultData.error("缺少题干");
        }
        if (body.containsKey("keyword") && !body.get("keyword").toString().isEmpty()) {
            keyword = body.get("keyword").toString();
        } else {
            return ResultData.error("缺少关键词");
        }

        if (body.containsKey("option") && !body.get("option").toString().isEmpty()) {
            System.out.println(body.get("option"));
            String getOption = "";
            getOption = body.get("option").toString();
            option = getOption.split(",");
        } else {
            return ResultData.error("缺少选项");
        }

        if (body.containsKey("answer") && !body.get("answer").toString().isEmpty()) {
            String getAnswer = "";
            getAnswer = body.get("answer").toString();
            answer = getAnswer.split(",");
//            answer = body.get("answer").toString();
        } else {
            return ResultData.error("缺少答案");
        }

        if (body.containsKey("typeId")) {
            type = questionService.findById(Long.valueOf(body.get("typeId").toString()));
            if (type == null) {
                return ResultData.error("typeId选择不正确");
            }
        } else {
            return ResultData.error("缺少typeId");
        }
        try {
            Question question = questionService.create(title, answer, option, type, keyword);
            return ResultData.success(question);
        } catch (Exception ex) {
            return ResultData.error(ex.getMessage());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Map body) {
        Question question = questionService.get(id);
        if (question == null) {
            return ResultData.error("该试题不存在");
        }
        Type type = question.getType();
        Long typeId = type.getId();
        String title = "";
        String[] answer = null;
        String[] option = null;
        if (body.containsKey("title") && !body.get("title").toString().isEmpty()) {
            title = body.get("title").toString().trim();
        }
        if (body.containsKey("option") && !body.get("option").toString().isEmpty()) {
            String getOption = "";
            getOption = body.get("option").toString();
            option = getOption.split(",");
        } else {
            return ResultData.error("缺少选项");
        }
        if (body.containsKey("answer") && !body.get("answer").toString().isEmpty()) {
            String getAnswer = "";
            getAnswer = body.get("answer").toString();
            answer = getAnswer.split(",");
        } else {
            return ResultData.error("缺少答案");
        }
        // 这个判断只能在service里做 吧
//            if (body.containsKey("option") && !body.get("option").toString().isEmpty()) {
//            if (typeId == 1 || typeId == 2) {
//                if (option.length == 4) {
//                    String getOption = "";
//                    getOption = body.get("option").toString();
//                    option = getOption.split(",");
//                } else {
//                    return new ResponseEntity<>("option项数应为4", HttpStatus.BAD_REQUEST);
//                }
//            } else if (typeId == 3) {
//                if (option.length == 2) {
//                    String getOption = "";
//                    getOption = body.get("option").toString();
//                    option = getOption.split(",");
//                } else {
//                    return new ResponseEntity<>("option项数应为2", HttpStatus.BAD_REQUEST);
//                }
//            }
//            }
//        if (typeId == 1 || typeId == 3){
//            if (answer.length == 1 && body.containsKey("answer") && !body.get("answer").toString().isEmpty()) {
//                String getAnswer = "";
//                getAnswer = body.get("answer").toString();
//                answer = getAnswer.split(",");
//            }
//        } else if (typeId == 2) {
//            if (answer.length > 1 && answer.length <= 4 && body.containsKey("answer") && !body.get("answer").toString().isEmpty()) {
//                String getAnswer = "";
//                getAnswer = body.get("answer").toString();
//                answer = getAnswer.split(",");
//            }
//        }
        try {
            return ResultData.success(questionService.update(question, answer, title, option));
        } catch (Exception e) {
            return ResultData.error(e.getMessage());
        }
    }
    // 删除试题
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id) {
        Question question = questionService.get(id);
        if (question == null) {
            return ResultData.error("id参数不正确");
        }
        questionService.delete(question);
        return ResultData.success();
    }

    // 根据id获取试题详情 isDeleted == 0
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable Long id) {
        Question question = questionService.get(id);
        if (question != null) {
            return ResultData.success(question);
        } else {
            return ResultData.error("该试题不存在");
        }
    }

}
