package com.init.online_examination.service;

import com.init.online_examination.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class GradeService {
    private GradeRepository gradeRepository;
    private QuestionRepository questionRepository;
    private UserRepository userRepository;

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
        List<Question> questions = examPaper.getQuestions();
        for (int i = 0; i < body.size(); i++){
            Long questId = Long.valueOf(body.get(i).get("questId").toString());
            String[] answer = body.get(i).get("answer").toString().split(",");
            Question question = questionRepository.findFirstByIdAndIsDeleted(questId, 0);
            String[] rightAnswer = question.getAnswer();
            Float perValue = question.getPerValue();
            // 要求做题时必须都做出选择，不能空着
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
        grade.setGrade(currentGrade);
        gradeRepository.save(grade);
        return grade;
    }
}
