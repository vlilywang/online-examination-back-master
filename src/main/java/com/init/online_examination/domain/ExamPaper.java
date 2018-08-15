package com.init.online_examination.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "exampaper")
public class ExamPaper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "duration")
    private Integer Duration; // 持续时间，固定值 60 分钟
    @Column(name = "single_amount")
    private Integer singleAmount;
    @Column(name = "multi_amount")
    private Integer multiAmount;
    @Column(name = "judge_amount")
    private Integer judgeAmount;
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
    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
    private List<Question> questions;
//    @ManyToMany(cascade = {CascadeType.PERSIST})
//    @JoinTable(name = "QUESTION_EXAMPAPER",
//            joinColumns = @JoinColumn(name = "QUESTION_ID", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "EXAMPAPER_ID", referencedColumnName = "id"))
//    private List<Question> questions;
//    @JoinTable(name = "question_exampaper", joinColumns = {@JoinColumn(name = "qid", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "pid", referencedColumnName = "id")})
// ????
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public Integer getSingleAmount() {
        return singleAmount;
    }

    public void setSingleAmount(Integer singleAmount) {
        this.singleAmount = singleAmount;
    }

    public Integer getMultiAmount() {
        return multiAmount;
    }

    public void setMultiAmount(Integer multiAmount) {
        this.multiAmount = multiAmount;
    }

    public Integer getJudgeAmount() {
        return judgeAmount;
    }

    public void setJudgeAmount(Integer judgeAmount) {
        this.judgeAmount = judgeAmount;
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

    public Integer getDuration() {
        return Duration;
    }

    public void setDuration(Integer duration) {
        Duration = duration;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }
}
