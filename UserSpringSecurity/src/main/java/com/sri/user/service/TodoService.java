package com.sri.user.service;

import java.util.List;

import com.sri.user.Entity.TodoEntity;

public interface TodoService {

	public List<TodoEntity> getAll();
	
	public void addToDo(TodoEntity todo);

	public void updateToDo(TodoEntity todo);

	public void deleteToDo(int id);
	
	
}
