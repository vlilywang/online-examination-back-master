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
import java.util.Map;

@Service
public class GradeService {
    private GradeRepository gradeRepository;
    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private ExamPaperRepository examPaperRepository;

    @Autowired
    public void setExamPaperRepository(ExamPaperRepository examPaperRepository) {
        this.examPaperRepository = examPaperRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Autowired
    public void setGradeRepository(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }
//
//    public Grade findGradeByIds(Long userId, Long exampaperId) {
//        return gradeRepository.findFirstByUserIdAndExamId(userId, exampaperId);
//    }

    public Page<Grade> find(Date beginTime, Date endTime, User user, ExamPaper examPaper, Integer page, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Specification<Grade> specification = new Specification<Grade>() {
            @Override
            public Predicate toPredicate(Root<Grade> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (user != null) {
                    predicateList.add(criteriaBuilder.isNotNull(root.get("user")));
                    predicateList.add(criteriaBuilder.equal(root.get("user").as(User.class), user));
                }
                if (examPaper != null) {
                    predicateList.add(criteriaBuilder.isNotNull(root.get("examPaper")));
                    predicateList.add(criteriaBuilder.equal(root.get("examPaper").as(ExamPaper.class), examPaper));
                }
                if (beginTime != null || endTime != null) {
                    predicateList.add(criteriaBuilder.isNotNull(root.get("createTime")));
                }
                if (beginTime != null) {
                    predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Date.class), beginTime));
                }
                if (endTime != null) {
                    predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Date.class), endTime));
                }
                Predicate[] arrayType = new Predicate[predicateList.size()];
                return criteriaBuilder.and(predicateList.toArray(arrayType));
            }
        };
        return gradeRepository.findAll(specification, PageRequest.of(page - 1, pageSize, sort));
    }

    // 获取全部成绩 不分页 什么都不分
    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    // 根据考试id获取考试详情
    public Grade findGradeById(Long id){
        return gradeRepository.findFirstById(id);
    }
    // 获取当前用户参加的所有考试的详情
    public List<Grade> findCurrentAll(User user) {
        return gradeRepository.findAllByUser(user);
    }
    // 根据试卷获取
    public List<Grade> findPaperAll(ExamPaper paper) {
        return gradeRepository.findAllByExamPaper(paper);
    }
    // 新建
    public Grade add(ExamPaper examPaper, User user, List<Map> body){
        Date current = new Date();
        Grade grade = new Grade();
        grade.setCreateTime(current);
        grade.setUser(user);
        grade.setExamPaper(examPaper);
        Float currentGrade = 0F;
        for (int i = 0; i < body.size(); i++){
            Long questId = Long.valueOf(body.get(i).get("questId").toString());
            String[] answer = body.get(i).get("answer").toString().split(",");
            Question question = questionRepository.findFirstByIdAndIsDeleted(questId, 0);
            if (question != null) {
                String[] rightAnswer = question.getAnswer();
                Float perValue = question.getPerValue();
                if(answer.length != rightAnswer.length) {
                    currentGrade = currentGrade;
                } else {
                    boolean flag = false;
                    for (int a = 0; a < answer.length; a++) {
                        for (int j = 0; j < rightAnswer.length; j++) {
                            if (answer[a].equals(rightAnswer[j])) {
                                flag = true;
                                break;
                            } else {
                                flag = false;
                            }
                        }
                        if(flag == false) {
                            break;
                        }
                    }
                    if (flag == true) {
                        currentGrade = currentGrade + perValue;
                    }
                }
            }
        }
        grade.setGrade(currentGrade);
        gradeRepository.save(grade);

        user.setIsUsed(1);
        userRepository.save(user);
        examPaper.setIsUsed(1);
        examPaperRepository.save(examPaper);
        return grade;
    }

    public List<Grade> findAllByUserAndExamPaper(User user, ExamPaper examPaper) {
        return this.gradeRepository.findAllByUserAndExamPaper(user, examPaper);
    }
    public Long count() {
        return gradeRepository.count();
    }
    public Long countByUser(User user) {
        return gradeRepository.countByUser(user.getId());
    }
    public Long countByExampaper(ExamPaper examPaper) {
        return gradeRepository.countByExampaper(examPaper.getId());
    }
}

