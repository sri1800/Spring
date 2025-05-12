package com.sri.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sri.user.Entity.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity,Long>{
	 UserEntity findByEmail(String email);

	UserEntity findByUserId(String userId);
}
