package com.init.online_examination.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findFirstById(Long id);
}