package com.example.multitenancy.models;

import java.time.Instant;
import java.util.UUID;

public class Todo {
	
	private UUID todoId;
	private UUID userId;
	private String description;
	private Instant createdAt;
	private boolean completed;
	
	
	public UUID getTodoId() {
		return todoId;
	}
	public void setTodoId(UUID todoId) {
		this.todoId = todoId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Instant getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	
}
