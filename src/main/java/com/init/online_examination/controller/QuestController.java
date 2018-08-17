package com.init.online_examination.controller;

import com.init.online_examination.domain.*;
import com.init.online_examination.service.QuestionService;
import com.init.online_examination.utilty.PageData;
import com.init.online_examination.utilty.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/question")
// 搜索接口的关键词
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
                               @RequestParam(defaultValue = "") String keyword,
                               @RequestParam(defaultValue = "0") Long typeId,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        Type type = null;
        if (typeId != 0) {
            type = questionService.findById(typeId);
            if (type == null) {
                return ResultData.error("指定的试题类型id不正确");
            }
        }
//        Long count = questionService.count();
        Page<Question> questions = questionService.find(beginTime, endTime, keyword, type, page, pageSize);
//        return ResultData.success(questions);
        return ResultData.success(new PageData(questions, page, pageSize));
    }

    // 获取试题列表 不分页
    @RequestMapping(value = "/noPage", method = RequestMethod.GET)
    public ResponseEntity list() {
        return ResultData.success(questionService.list());
    }

    // 新建试题
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasRole('TEACHER')")
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
        if (body.containsKey("typeId")) {
            type = questionService.findById(Long.valueOf(body.get("typeId").toString()));
            if (type == null) {
                return ResultData.error("typeId选择不正确");
            }
        } else {
            return ResultData.error("缺少typeId");
        }
        Long typeId = Long.valueOf(body.get("typeId").toString());
        if (body.containsKey("option") && !body.get("option").toString().isEmpty()) {
            String getOption = "";
            getOption = body.get("option").toString();
            option = getOption.split(",");
            if (typeId == 1L || typeId == 2L){
                if (option.length != 4) {
                    return ResultData.error("选择题选项应为四项");
                }
            } else if (typeId == 3L) {
                if (option.length != 2) {
                    return ResultData.error("判断题选项应为两项");
                }
            }
        } else {
            return ResultData.error("缺少选项");
        }
        if (body.containsKey("answer") && !body.get("answer").toString().isEmpty()) {
            String getAnswer = "";
            getAnswer = body.get("answer").toString();
            answer = getAnswer.split(",");
            if (typeId == 1L || typeId == 3L){
                if (answer.length != 1) {
                    return ResultData.error("单选题或判断题答案应该唯一");
                }
            } else if (typeId == 2L) {
                if (answer.length < 2 || answer.length > 4) {
                    return ResultData.error("多选题答案数目有误");
                }
            }
        } else {
            return ResultData.error("缺少答案");
        }
        try {
            Question question = questionService.create(title, answer, option, type, keyword);
            return ResultData.success(question);
        } catch (Exception ex) {
            return ResultData.error(ex.getMessage());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('TEACHER')")
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
        String keyword = "";
        if (body.containsKey("title") && !body.get("title").toString().isEmpty()) {
            title = body.get("title").toString().trim();
        }
        if (body.containsKey("keyword") && !body.get("keyword").toString().isEmpty()) {
            keyword = body.get("keyword").toString();
        } else {
            return ResultData.error("缺少关键词");
        }
        if (body.containsKey("option") && !body.get("option").toString().isEmpty()) {
            String getOption = "";
            getOption = body.get("option").toString();
            option = getOption.split(",");
            if (typeId == 1L || typeId == 2L){
                if (option.length != 4) {
                    return ResultData.error("选择题选项应为四项");
                }
            } else if (typeId == 3L) {
                if (option.length != 2) {
                    return ResultData.error("判断题选项应为两项");
                }
            }
        } else {
            return ResultData.error("缺少选项");
        }
        if (body.containsKey("answer") && !body.get("answer").toString().isEmpty()) {
            String getAnswer = "";
            getAnswer = body.get("answer").toString();
            answer = getAnswer.split(",");
            if (typeId == 1L || typeId == 3L){
                if (answer.length != 1) {
                    return ResultData.error("单选题或判断题答案应该唯一");
                }
            } else if (typeId == 2L) {
                if (answer.length < 2 || answer.length > 4) {
                    return ResultData.error("多选题答案数目有误");
                }
            }
        } else {
            return ResultData.error("缺少答案");
        }
        try {
            return ResultData.success(questionService.update(question, answer, title, option, keyword));
        } catch (Exception e) {
            return ResultData.error(e.getMessage());
        }
    }
    // 生成试卷后修改试题is_used状态
    @RequestMapping(value = "/update/isUsed/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity updateIsUsed(@PathVariable Long id, @RequestBody Map body) throws Exception {
        Integer isUsed = null;
        if (body.containsKey("isUsed") && !body.get("isUsed").toString().isEmpty()) {
            isUsed = Integer.valueOf(body.get("isUsed").toString());
        } else {
            return ResultData.error("缺少是否使用的状态");
        }
        if (isUsed != 0 && isUsed != 1) {
            return ResultData.error("isUsed的值只能是0或者1");
        }
        Question question = questionService.get(id);
        if (question == null) {
            return ResultData.error("该试题不存在");
        }
        return ResultData.success(questionService.update(question, isUsed));
    }

    // 删除试题
    @RequestMapping(value = "/delete/id/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity delete(@PathVariable Long id) {
        Question question = questionService.get(id);
        if (question == null) {
            return ResultData.error("id参数不正确");
        }
        if (question.getIsUsed().equals(0)) {
            questionService.delete(question);
        } else {
            return ResultData.error("该试题已被试卷选中，不能删除");
        }
        return ResultData.success();
    }
    // 批量删除试题
    @RequestMapping(value = "/delete/ids/{ids}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity deleteIds(@PathVariable List<Long> ids) {
        List<Question> questions = new ArrayList<>();

//        List<User> users = new ArrayList<>();
        for (Integer i = 0; i < ids.size(); i++) {
            Question question = questionService.get(ids.get(i));
            if (question != null && question.getIsUsed().equals(0)) {
                questions.add(question);
            } else {
                return ResultData.error("试题不存在/试题已被试卷选中，不能删");
            }
        }
        try{
            for (Integer i = 0; i< questions.size(); i++) {
                questionService.delete(questions.get(i));
            }
            return ResultData.success();
        } catch (Exception e) {
            return ResultData.error(e.getMessage());
        }
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

    // 根据试题id获取选项
//    @RequestMapping(value = "/{id}/options", method = RequestMethod.GET)
//    public ResponseEntity getOptionsByQuestId(@PathVariable Long id) {
//        Question question = questionService.get(id);
//        if (question != null) {
//            List<Option> options = questionService.listOptions(question);
//            return ResultData.success(questionService.listOptions(question));
//        } else {
//            return ResultData.error("该试题不存在");
//        }
//    }
}
