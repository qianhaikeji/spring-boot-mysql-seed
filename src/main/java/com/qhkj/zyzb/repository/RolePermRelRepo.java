package com.qhkj.zyzb.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qhkj.zyzb.models.RolePermRel;

public interface RolePermRelRepo extends JpaRepository<RolePermRel, Integer> {
    List<RolePermRel> findByRoleId(Integer rid);
}
