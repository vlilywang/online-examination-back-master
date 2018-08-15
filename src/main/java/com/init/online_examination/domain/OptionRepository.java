package com.init.online_examination.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
//    Option findFirstById(Long qid);
    Option findFirstByOptionsAndSingnal(Question question, String singnal);

    @Query(value = "SELECT * FROM options where option_id = ?1 and singnal=?2", nativeQuery = true)
    Option findOption(Long optionId, String singnal);

//    List<Option> findAllByQuestion(Question question);
}
