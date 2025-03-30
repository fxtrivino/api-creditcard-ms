package com.bci.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bci.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

