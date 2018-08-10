package com.init.online_examination.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
    Option findFirstById(Long qid);
    Option findFirstByQuestionAndSingnal(Question question, String singnal);
}
