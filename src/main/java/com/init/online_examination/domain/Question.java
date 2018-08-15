package com.init.online_examination.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "answer")
    private String[] answer;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;
    @Column(name = "per_value")
    private Float perValue; // 分值
    @Column(name = "create_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Column(name = "keyword")
    private String keyword;
    @Column(name = "is_deleted")
    @JsonIgnore
    private Integer isDeleted;
    @Column(name = "is_used")
    @JsonIgnore
    private Integer isUsed;
    @Column(name = "used_times")
    @JsonIgnore
    private Integer usedTimes;
    //    @ManyToMany(mappedBy="questions", cascade = {CascadeType.PERSIST})
//    private List<ExamPaper> exampaper;//试题持有的试卷的集合？
//
    @OneToMany
    @JoinColumn(name = "option_id")
    private List<Option> options;

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String[] getAnswer() {
        return answer;
    }

    public void setAnswer(String[] answer) {
        this.answer = answer;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Float getPerValue() {
        return perValue;
    }

    public void setPerValue(Float perValue) {
        this.perValue = perValue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public Integer getUsedTimes() {
        return usedTimes;
    }

    public void setUsedTimes(Integer usedTimes) {
        this.usedTimes = usedTimes;
    }
}
