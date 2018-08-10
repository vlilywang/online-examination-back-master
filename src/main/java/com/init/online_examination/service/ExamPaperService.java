package com.init.online_examination.service;

import com.init.online_examination.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExamPaperService {
    private ExamPaperRepository examPaperRepository;
    private QuestionRepository questionRepository;

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Autowired
    public void setExamPaperRepository(ExamPaperRepository examPaperRepository) {
        this.examPaperRepository = examPaperRepository;
    }

    // 分页查询
    public Page<ExamPaper> find(Date beginTime, Date endTime, String[] keyword, Integer page, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Specification<ExamPaper> specification = new Specification<ExamPaper>() {
            @Override
            public Predicate toPredicate(Root<ExamPaper> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (keyword != null && keyword.length != 0) {
                    predicateList.add(criteriaBuilder.isNotNull(root.get("keyword")));
                    predicateList.add(criteriaBuilder.like(root.get("keyword").as(String.class), "%" + keyword + "%"));
                }
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
        return examPaperRepository.findAll(specification, PageRequest.of(page -1, pageSize, sort));
    }

    // 生成试卷
    public ExamPaper create(String title, String keyword, Integer singleAmount, Integer multiAmount, Integer judgeAmount ) throws Exception {
        Date current = new Date();
        ExamPaper examPaper = new ExamPaper();
        examPaper.setCreateTime(current);
        examPaper.setIsDeleted(0);
        examPaper.setSingleAmount(singleAmount);
        examPaper.setMultiAmount(multiAmount);
        examPaper.setJudgeAmount(judgeAmount);
        examPaper.setDuration(60);
        examPaper.setKeyword(keyword);
        examPaper.setTitle(title);
        System.out.println("singleAmount::" + singleAmount);
        List<Question> questions = questionRepository.getQuestionsRand(1L, singleAmount);
        examPaper.setQuestions(questions);
        examPaperRepository.save(examPaper);
        return examPaper;
    }

    // 获取试卷列表 不分页
    public List<ExamPaper> list() {
        return examPaperRepository.findAllByIsDeleted(0);
    }
    // 根据id获取仍有效的试卷
    public ExamPaper get(Long id) {
        return examPaperRepository.findFirstByIdAndIsDeleted(id, 0);
    }

    // 删除试卷
    public void delete(ExamPaper examPaper) {
        examPaper.setIsDeleted(1);
        examPaperRepository.save(examPaper);
    }
}
