package com.qhkj.zyzb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qhkj.zyzb.models.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    public Role findByName(String name);
}
