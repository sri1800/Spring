package com.sri.user.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sri.user.pojo.CreateTodoRequestModel;
import com.sri.user.pojo.CreateTodoResponseModel;
import com.sri.user.pojo.TodoDto;
import com.sri.user.service.TodoService;

@RestController
@RequestMapping("/todo")
public class TodoController {

		@Autowired
		private TodoService todoService;
		
		@Autowired
		private ModelMapper mapper;
		 
		 @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 @PostMapping("/createtodo")
		    public ResponseEntity<CreateTodoResponseModel> createToDo(@Validated @RequestBody CreateTodoRequestModel createTodoRequestModel,Authentication authentication) {
			 	TodoDto todoDto=mapper.map(createTodoRequestModel, TodoDto.class);
			 	boolean isAdmin = authentication.getAuthorities().stream().anyMatch(authority->authority.getAuthority().equals("ROLE_ADMIN"));
			 	TodoDto CreatedTodo=todoService.addToDo(todoDto);
			 	if(isAdmin) {
			 		CreateTodoResponseModel response=mapper.map(CreatedTodo, CreateTodoResponseModel.class);
			 		return ResponseEntity.status(HttpStatus.CREATED).body(response);
			 	}
			 	else {
			 		return ResponseEntity.status(HttpStatus.CREATED).build();
			 	}
		    }
		 
		 
		 @GetMapping("/data")
		    public ResponseEntity<List<CreateTodoResponseModel>> getAllToDos() {
		      List<TodoDto> todoDto= todoService.getAllTodo();
		      List<CreateTodoResponseModel>  response=todoDto.stream().map(todo->mapper.map(todo,CreateTodoResponseModel.class)).toList();
		      return ResponseEntity.ok().body(response);
		    }
		 
		 @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 @PutMapping("/{id}")
		    public ResponseEntity<CreateTodoResponseModel> updateToDo(@PathVariable Long id, @RequestBody CreateTodoRequestModel createTodoRequestModel) {
			 	TodoDto todoDto=mapper.map(createTodoRequestModel, TodoDto.class);
			 	TodoDto UpdatedTodo=todoService.updateToDo(id, todoDto);
			 	CreateTodoResponseModel response=mapper.map(UpdatedTodo, CreateTodoResponseModel.class);
			 	return ResponseEntity.ok().body(response);
		    }
		 
		 @PreAuthorize("hasRole('ADMIN')")
		 @DeleteMapping("/{id}")
		    public ResponseEntity<String> deleteToDo(@PathVariable Long id) {
			 todoService.deleteToDo(id);
			 return ResponseEntity.ok().body("Deletion Successfull");
		    }
		 
		
}
