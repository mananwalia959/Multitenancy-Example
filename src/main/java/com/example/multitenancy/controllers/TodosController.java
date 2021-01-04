package com.example.multitenancy.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.multitenancy.models.Todo;
import com.example.multitenancy.models.TodoRequest;
import com.example.multitenancy.service.TodoService;

@RestController
@RequestMapping("/api")
public class TodosController {
	
	@Autowired private TodoService todoService;
	
	@GetMapping(path = "/todos")
	List<Todo> getAllTodosByUser(){
		return todoService.getAllTodosByUser();
	}
	
	@GetMapping(path = "/todos/{todoId}")
	Todo getSingleTodo(@PathVariable("todoId") UUID todoId){
		return todoService.getSingleTodoByUser(todoId);
	}
	
	@PostMapping(path = "/todos")
	Todo createTodo(@RequestBody TodoRequest request) {
		return todoService.createTodo(request);
	}
	
	@PatchMapping(path = "/todos/{todoId}")
	Todo updateTodo(@RequestBody TodoRequest request,@PathVariable("todoId") UUID todoId) {
		return todoService.updateTodo(request,todoId );
	}
	
	

}
