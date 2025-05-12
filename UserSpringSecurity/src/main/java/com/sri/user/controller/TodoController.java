package com.sri.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sri.user.Entity.TodoEntity;
import com.sri.user.service.TodoService;



@RestController
@RequestMapping("/todo")
public class TodoController {

		@Autowired
		private TodoService poJoService;
		
		 @GetMapping("/data")
		    public List<TodoEntity> getAllToDos() {
		        return poJoService.getAll();
		    }
		 
		 @PostMapping
		    public void createToDo(@RequestBody TodoEntity toDo) {
		        poJoService.addToDo(toDo);
		    }
		 
		 @PutMapping("/{id}")
		    public void updateToDo(@PathVariable int id, @RequestBody TodoEntity todo) {
		        todo.setId(id);
		        poJoService.updateToDo(todo);
		    }
		 
		 @DeleteMapping("/{id}")
		    public void deleteToDo(@PathVariable int id) {
		        poJoService.deleteToDo(id);
		    }
		 
		
}
