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
//    @ManyToOne
//    @JoinColumn(name = "question_id")
//    private Question question;

//    public Question getQuestion() {
//        return question;
//    }
//
//    public void setQuestion(Question question) {
//        this.question = question;
//    }


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

    public String getSingnal() {
        return singnal;
    }

    public void setSingnal(String singnal) {
        this.singnal = singnal;
    }
}
