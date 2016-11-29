package com.qhkj.seed.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.qhkj.seed.models.Admin;

public interface AdminRepo extends JpaRepository<Admin, Integer> {
    public Admin findByUsername(String username);
    public Page<Admin> findAll(Pageable pageRequest);
}
