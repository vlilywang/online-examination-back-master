package com.init.online_examination.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExamPaperRepository extends JpaRepository<ExamPaper, Long>, JpaSpecificationExecutor<ExamPaper> {
    ExamPaper findFirstByIdAndIsDeleted(Long id, Integer isDeleted);
    List<ExamPaper> findAllByIsDeleted(Integer isDeleted);

    @Query(value = "SELECT COUNT(*) FROM exampaper where is_deleted=0 ", nativeQuery = true)
    Long countByIsDeleted();
//     @Query(value = "SELECT * FROM question WHERE topic_type=:topicType ORDER BY rand() LIMIT :numbers", nativeQuery = true)
    // "SELECT * FROM table ORDER BY RAND() LIMIT n"
//    @Query(value = "select * from question order by rand() limit ?1")
}
