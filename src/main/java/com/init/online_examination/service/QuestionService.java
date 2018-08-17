package com.init.online_examination.service;

import com.init.online_examination.domain.*;
import com.init.online_examination.utilty.ResultData;
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
public class QuestionService {
    private QuestionRepository questionRepository;
    private TypeRepository typeRepository;
    private OptionRepository optionRepository;

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Autowired
    public void setTypeRepository(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Autowired
    public void setOptionRepository(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    // 获取试题列表 分页
    public Page<Question> find(Date beginTime, Date endTime, String keyword, Type type, Integer page, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Specification<Question> specification = new Specification<Question>() {
            @Override
            public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                predicateList.add(criteriaBuilder.equal(root.get("isDeleted").as(Integer.class), 0));
                if (type != null) {
                    predicateList.add(criteriaBuilder.isNotNull(root.get("type")));
                    predicateList.add(criteriaBuilder.equal(root.get("type").as(Type.class), type));
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
                if (keyword != null && !keyword.isEmpty()) {
                    String likeWord = "%" + keyword + "%";
                    Predicate[] likePredicate = new Predicate[2];
                    likePredicate[0] = criteriaBuilder.like(root.get("title").as(String.class), likeWord);
                    likePredicate[1] = criteriaBuilder.like(root.get("keyword").as(String.class), likeWord);
                    predicateList.add(criteriaBuilder.or(likePredicate));
                }
                Predicate[] arrayType = new Predicate[predicateList.size()];
                return criteriaBuilder.and(predicateList.toArray(arrayType));
            }
        };
        return questionRepository.findAll(specification, PageRequest.of(page - 1, pageSize, sort));
    }

    // 获取试题列表 不分页
    public List<Question> list() {
        return questionRepository.findAllByIsDeleted(0);
    }

    // 根据试题类型获取试题列表 不分页
    public List<Question> listByTypeId(Type type) {
        return questionRepository.findAllByTypeAndIsDeleted(type, 0);
    }

    // 根据id获取试题详情
    public Question get(Long id) {
//        return questionRepository.findFirstByIdAndIsDeleted(id, 0);
        return questionRepository.findDetailByIdAndIsDeleted(id, 0);
    }

    // 添加选项
//    public Option createOption(String options, Question question, String singnal){
    public Option createOption(String options, String singnal) { // 改变的
        Option option = new Option();
        option.setOptions(options);
//        option.setQuestion(question); // 改变的
        option.setSingnal(singnal);
        optionRepository.save(option);
        return option;
    }

    // 新建试题
    public Question create(String title, String[] answer, String[] option, Type type, String keyword) throws Exception {
        Date current = new Date();
        Question question = new Question();
        question.setTitle(title);
        question.setType(type);
        question.setKeyword(keyword);
        question.setCreateTime(current);
        question.setIsDeleted(0);
        // 此处的answer应该给与判断，
        question.setAnswer(answer);
        question.setIsUsed(0);
        question.setUsedTimes(0);
        int typeId = type.getId().intValue();
        if (typeId == 1) {// 单选题
            question.setPerValue(1F);
        } else if (typeId == 2) {// 多选题
            question.setPerValue(2F);
        } else if (typeId == 3) {// 判断题
            question.setPerValue(0.5F);
        }
        List<Option> options = new ArrayList<Option>();
//        questionRepository.save(question); // 改变的
        String[] singnals = {"A", "B", "C", "D"};
        if (typeId == 1 || typeId == 2) {// 单选题
            if (option.length == 4) {
                for (int i = 0; i < option.length; i++) {
//                    Option option1 = this.createOption(option[i], question, singnals[i]);// 改变的
                    Option option1 = this.createOption(option[i], singnals[i]);// 改变的
                    options.add(option1);
                }
            }
        } else if (typeId == 3) {// 判断题
            if (option.length == 2) {
                // A 对 B 错
                for (int i = 0; i < option.length; i++) {
//                    Option option1 = this.createOption(option[i], question, singnals[i]);// 改变的
                    Option option1 = this.createOption(option[i], singnals[i]);// 改变的
                    options.add(option1);
                }
            }
        }
        question.setOptions(options);
        questionRepository.save(question); // 改变的
        return question;
    }

    // 修改试题is_used状态
    public Question update(Question question, Integer isUsed) throws Exception {
        question.setIsUsed(isUsed);
        questionRepository.save(question);
        return question;
    }
    public Question update(Question question, String[] answer, String title, String[] option, String keyword) throws Exception {
        if (title != null && !title.isEmpty()) {
            question.setTitle(title);
        }
        // 判断
        if (answer != null && !title.isEmpty()) {
            question.setAnswer(answer);
        }
        if (keyword != null && !keyword.isEmpty()) {
            question.setKeyword(keyword);
        }
        List<Option> options = new ArrayList<Option>();
        String[] singnals = {"A", "B", "C", "D"};
        for (int i = 0; i < option.length; i++) {
            Option option1 = this.findOption(question.getId(), singnals[i]);
            if (option1 != null) {
                option1.setOptions(option[i]); // 改变
                optionRepository.save(option1);
                options.add(option1);
            }
        }
        question.setOptions(options);
        questionRepository.save(question);
        return question;
    }

    // 根据id获取type
    public Type findById(Long id) {
        return typeRepository.findFirstById(id);
    }

    //    public Option findOptionByQuestAndSingnal(Question question, String singnal){
//        return optionRepository.findFirstByQuestionAndSingnal(question, singnal);
//    }
    public Option findOption(Long opId, String singnal) {
        return optionRepository.findOption(opId, singnal);
    }

    public Integer countByTypeAndIsDeleted(Type type, Integer isDelted) {
        return questionRepository.countByTypeAndIsDeleted(type, isDelted);
    }

    // 删除试题
    public void delete(Question question) {
        question.setIsDeleted(1);
        questionRepository.save(question);
    }

    public Long count() {
        return questionRepository.countByIsDeleted();
//        return questionRepository.count();
    }

//    public List<Option> listOptions(Question question) {
//        return optionRepository.findAllByQuestion(question);
//    }

}
