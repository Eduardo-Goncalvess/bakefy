package com.cefet.bakefy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.bakefy.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
