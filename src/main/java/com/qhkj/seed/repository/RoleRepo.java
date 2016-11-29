package com.qhkj.seed.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qhkj.seed.models.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    public Role findByName(String name);
}
