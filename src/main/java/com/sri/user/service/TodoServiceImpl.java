package com.sri.user.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sri.user.Entity.TodoEntity;
import com.sri.user.Exception.TodoNotFoundException;
import com.sri.user.pojo.TodoDto;
import com.sri.user.repo.TodoRepo;
import com.sri.user.repo.UserRepo;
import com.sri.user.security.UserPrinciple;

@Service
public class TodoServiceImpl implements TodoService {
	
	@Autowired
	public TodoRepo todorepo;
	
	@Autowired
	public UserRepo userRepo;
	
	@Autowired
	ModelMapper mapper;

	@Override
	public List<TodoDto> getAllTodo() {
		List<TodoEntity> todoEntities = todorepo.findAll();
		List<TodoDto> todoDto=todoEntities.stream().map(todo->mapper.map(todo,TodoDto.class)).toList();
		return todoDto;
	}

	@Override
	public TodoDto addToDo(TodoDto todoDto) {
		TodoEntity todoEntity=mapper.map(todoDto,TodoEntity.class);
		String userId=((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
		
		todoEntity.setUsers(userRepo.findByUserId(userId));
		todorepo.save(todoEntity);
		TodoDto ReturnValue=mapper.map(todoEntity,TodoDto.class);
		return ReturnValue;
	}

	@Override
	public TodoDto updateToDo(Long id,TodoDto todoDto) throws TodoNotFoundException{
		Optional<TodoEntity> todoEntity=todorepo.findById(id);
		if(todoEntity.isEmpty()) {
			throw new TodoNotFoundException("Todo doesn't exist with id " + id);
		}
			TodoEntity todoE=todoEntity.get();
			todoE.setName(todoDto.getName());
			todoE.setDescription(todoDto.getDescription());
			todoE.setStartTime(todoDto.getStartTime());
			todoE.setEndTime(todoDto.getEndTime());
			todorepo.save(todoE);
		
		TodoDto ReturnValue=mapper.map(todoE,TodoDto.class);
		return ReturnValue;
	}

	@Override
	public TodoDto deleteToDo(Long id) throws TodoNotFoundException {
		Optional<TodoEntity> todo = todorepo.findById(id);
		if(todo.isEmpty()) {
			throw new TodoNotFoundException("Todo doesn't exist with id " + id);
		}
		else {
			todorepo.deleteById(id);
			return mapper.map(todo,TodoDto.class);
		}
	}

	@Override
	public List<TodoDto> getATodo(Long todoId) {
		Optional<TodoEntity> todoEntities = todorepo.findById(todoId);
		List<TodoDto> todoDto=todoEntities.stream().map(todo->mapper.map(todo,TodoDto.class)).toList();
		return todoDto;
	}
	


}
