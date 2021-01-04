package com.example.multitenancy.db;

public class DatabaseProperties {
	private String connectionString;
	private String username;
	private String password;
	
	
	public DatabaseProperties(String connectionString, String username, String password) {
		this.connectionString = connectionString;
		this.username = username;
		this.password = password;
	}
	
	public String getConnectionString() {
		return connectionString;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	

	
}
