package com.sri.user.service;

import java.util.List;

import com.sri.user.pojo.TodoDto;

public interface TodoService {

	public List<TodoDto> getAllTodo();
	
	public TodoDto addToDo(TodoDto todoDto);

	public TodoDto updateToDo(Long Id,TodoDto todoDto);

	public void deleteToDo(Long id);
	
	
}
