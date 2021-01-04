package com.example.multitenancy.service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.example.multitenancy.auth.UserPrincipalContextHolder;
import com.example.multitenancy.db.TodoRepository;
import com.example.multitenancy.models.Todo;
import com.example.multitenancy.models.TodoRequest;

@Component
public class TodoService {
	@Autowired private TodoRepository todoRepository;
	

	public List<Todo> getAllTodosByUser() {
		var userPrincipal = UserPrincipalContextHolder.getContext();
		return todoRepository.getAllTodosByUser(userPrincipal.getUserID());
	}
	
	public Todo createTodo(TodoRequest request) {
		checkIfInvalidTodoRequest(request);
		var userPrincipal = UserPrincipalContextHolder.getContext();
		Todo todo = new Todo() ;
		todo.setTodoId(UUID.randomUUID());
		todo.setUserId(userPrincipal.getUserID());
		todo.setDescription(request.getDescription());
		todo.setCompleted(request.isCompleted());
		todo.setCreatedAt(Instant.now());
		todoRepository.saveNewTodo(todo);
		return todo;
	}
	
	private void checkIfInvalidTodoRequest(TodoRequest request) {
		if( Objects.isNull(request.getDescription()) || request.getDescription().isBlank())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "invalid todoRequest");
		
	}

	public Todo updateTodo(TodoRequest request , UUID todoId) {
		Todo todo = todoRepository.getTodoById(todoId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "todo not found"));
		var userPrincipal = UserPrincipalContextHolder.getContext();
		if(!todo.getUserId().equals(userPrincipal.getUserID()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User Access Violation");
		
		todo.setDescription(request.getDescription());
		todo.setCompleted(request.isCompleted());
		
		todoRepository.updateTodo(todo);
		return todo;
	}

	public Todo getSingleTodoByUser(UUID todoId) {
		Todo todo = todoRepository.getTodoById(todoId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "todo not found"));
		var userPrincipal = UserPrincipalContextHolder.getContext();
		if(!todo.getUserId().equals(userPrincipal.getUserID()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User Access Violation");
		return todo;
	}
	
	
	
	

}
