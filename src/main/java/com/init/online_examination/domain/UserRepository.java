package com.init.online_examination.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findFirstByIdAndIsDeleted(Long id, Integer isDeleted);
    User findFirstByUsernameAndIsDeleted(String username, Integer isDeleted);

    @Query(value = "SELECT COUNT(*) FROM `user` where is_deleted=0 ", nativeQuery = true)
    Long countByIsDeleted();
//    Page<User> findAll(Specification<User> specification, PageRequest of);

//    Page<User> findAllByIsDeleted(Specification<User> specification, PageRequest of, Integer isDeleted);
}
