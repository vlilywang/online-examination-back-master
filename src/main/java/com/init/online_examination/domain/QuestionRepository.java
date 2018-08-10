package com.init.online_examination.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {
    Question findFirstByIdAndIsDeleted(Long id, Integer isDeleted);
    List<Question> findAllByIsDeleted(Integer isDeleted);
    List<Question> findAllByTypeAndIsDeleted(Type type, Integer isDeleted);
    Integer countByTypeAndIsDeleted(Type type, Integer isDeleted);

    @Query(value = "SELECT * FROM question where type_id = ?1 ORDER BY rand() LIMIT ?2", nativeQuery = true)
    List<Question> getQuestionsRand(Long typeId, Integer number);
//        @Query("select id from question")
//        Long[] findAllId();
//        @Query("select id from question where type_id = ?1 and is_deleted=0")
//        Long[] findAllIdByTypeId(Long typeId);
}