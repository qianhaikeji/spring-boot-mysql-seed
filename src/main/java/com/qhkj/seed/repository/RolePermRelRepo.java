package com.qhkj.seed.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qhkj.seed.models.RolePermRel;

public interface RolePermRelRepo extends JpaRepository<RolePermRel, Integer> {
    List<RolePermRel> findByRoleId(Integer rid);
}
