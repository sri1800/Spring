package com.sri.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sri.user.Entity.TodoEntity;
import com.sri.user.repo.TodoRepo;



@Service
public class TodoServiceImpl implements TodoService {
	
	@Autowired
	public TodoRepo pojorepo;

	@Override
	public List<TodoEntity> getAll() {
		// TODO Auto-generated method stub
		return TodoRepo.getAllToDo();
	}

	@Override
	public void addToDo(TodoEntity todo) {
		// TODO Auto-generated method stub
		 TodoRepo.addToDo(todo);
	}

	@Override
	public void updateToDo(TodoEntity todo) {
		// TODO Auto-generated method stub
		TodoRepo.update(todo);
	}

	@Override
	public void deleteToDo(int id) {
		// TODO Auto-generated method stub
		TodoRepo.delete(id);
	}

}
