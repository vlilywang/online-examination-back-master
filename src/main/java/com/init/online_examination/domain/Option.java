package com.init.online_examination.domain;

import javax.persistence.*;

@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "options")
    private String options;
    @Column(name = "singnal")
    private String singnal; // A B C D
    @Column(name = "r_w") // 这个没有必要了，可以删除
    private Integer riOrWr;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public Integer getRiOrWr() {
        return riOrWr;
    }

    public void setRiOrWr(Integer riOrWr) {
        this.riOrWr = riOrWr;
    }

    public String getSingnal() {
        return singnal;
    }

    public void setSingnal(String singnal) {
        this.singnal = singnal;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}
