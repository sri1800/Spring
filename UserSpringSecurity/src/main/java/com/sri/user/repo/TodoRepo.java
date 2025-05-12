package com.sri.user.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sri.user.Entity.TodoEntity;



@Repository
public interface TodoRepo extends JpaRepository<TodoEntity,Integer> {
	
	public static final List<TodoEntity> todos=new ArrayList<>();
	
	public static void addToDo(TodoEntity todo) {
		todos.add(todo);
	}
	public static List<TodoEntity> getAllToDo() {
        return todos;
    }
	
	public static void update(TodoEntity todo) {
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getId() == todo.getId()) {
                todos.set(i, todo);
                return;
            }
        }
    }
	
	public static void delete(int id) {
        todos.removeIf(todo -> todo.getId() == id);
    }
	
}
