package com.qhkj.seed.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.qhkj.seed.models.Permission;

public interface PermissionRepo extends JpaRepository<Permission, Integer> {
    @Query(value = "SELECT permission.* FROM role_perm_rel inner join permission on role_perm_rel.permId=permission.id WHERE roleId = ?1", nativeQuery = true)
    List<Permission> findByRoleId(@Param("rid") Integer rid);
}
