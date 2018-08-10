package com.init.online_examination.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
//    @Query(value = "SELECT * FROM grade where user_id = ?1 and exampapaer_id = ?2", nativeQuery = true)
//    Grade findFirstByUserIdAndExamId(Long userId, Long exampaperId);
//    List<Grade> findAll();
    Grade findFirstById(Long id);

    List<Grade> findAllByUser(User user);

    List<Grade> findAllByExamPaper(ExamPaper paper);
}
