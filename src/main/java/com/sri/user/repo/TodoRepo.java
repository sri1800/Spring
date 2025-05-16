package com.sri.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sri.user.Entity.TodoEntity;

@Repository
public interface TodoRepo extends JpaRepository<TodoEntity,Long> {
	
}



