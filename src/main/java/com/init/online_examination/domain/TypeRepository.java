package com.init.online_examination.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Long> {
    Type findFirstById(Long id);
}
