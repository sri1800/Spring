package com.sri.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sri.user.Entity.AuthoritiesEntity;


public interface AuthoritiesRepo extends JpaRepository<AuthoritiesEntity,Long>{
	AuthoritiesEntity findByName(String name);
}
