package com.sri.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sri.user.Entity.RoleEntity;

public interface RoleRepo extends JpaRepository<RoleEntity,Long>{

	RoleEntity findByName(String name);

}
