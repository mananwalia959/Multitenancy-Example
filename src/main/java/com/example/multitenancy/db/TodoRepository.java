package com.example.multitenancy.db;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.multitenancy.models.Todo;

public interface TodoRepository {
	
	
	List<Todo> getAllTodosByUser(UUID userId);
	
	
	Optional<Todo> getTodoById(UUID todoId);
	
	
	boolean saveNewTodo(Todo todo);
	
	boolean updateTodo(Todo todo);

}
