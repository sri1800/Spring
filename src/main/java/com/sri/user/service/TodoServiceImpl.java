package com.sri.user.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sri.user.Entity.TodoEntity;
import com.sri.user.pojo.TodoDto;
import com.sri.user.repo.TodoRepo;

@Service
public class TodoServiceImpl implements TodoService {
	
	@Autowired
	public TodoRepo todorepo;
	
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
		todorepo.save(todoEntity);
		TodoDto ReturnValue=mapper.map(todoEntity,TodoDto.class);
		return ReturnValue;
	}

	@Override
	public TodoDto updateToDo(Long id,TodoDto todoDto) {
		Optional<TodoEntity> todoEntity=todorepo.findById(id);
		if(id==null) return todoDto;
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
	public void deleteToDo(Long id) {
		todorepo.deleteById(id);
	}
	


}
