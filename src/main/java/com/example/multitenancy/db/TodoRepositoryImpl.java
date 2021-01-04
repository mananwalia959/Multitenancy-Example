package com.example.multitenancy.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.multitenancy.models.Todo;

@Component
public class TodoRepositoryImpl implements TodoRepository {
	
	@Autowired private MultiTenantConnectionProvider connectionProvider;

	@Override
	public List<Todo> getAllTodosByUser(UUID userId) {
		
		try (Connection conn = connectionProvider.getConnection();
				PreparedStatement stmt = getPreparedStatementForGetTodosByUser( conn, userId);
				ResultSet result = stmt.executeQuery()) {
			
			List<Todo> todos  = new ArrayList<>();
			while (result.next()) {
				Todo todo = extractTodoFromResultSet(result);
				todos.add(todo);
			}
			
			return todos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}




	@Override
	public Optional<Todo> getTodoById(UUID todoId) {
		try (Connection conn = connectionProvider.getConnection();
				PreparedStatement stmt = getPreparedStatementForGetTodoById( conn,todoId );
				ResultSet result = stmt.executeQuery()) {
			
			if (result.next()) {
				Todo todo = extractTodoFromResultSet(result);
				return Optional.of(todo);
			}
			
			return Optional.empty();

		} catch (SQLException e) { throw new RuntimeException(e); }
	}



	@Override
	public boolean saveNewTodo(Todo todo) {
		String query = "insert into todos (todoid,userid,completed,description,createdat) values (?,?,?,?,?)";
		
		try (Connection conn = connectionProvider.getConnection();
				PreparedStatement stmt = getPreparedStatementForSaveOrUpdateTodo( conn,query,todo ,false )) {
			
		return stmt.execute();

		} catch (SQLException e) { throw new RuntimeException(e); }
	}
	
	@Override
	public boolean updateTodo(Todo todo) {
		String query = "UPDATE todos SET todoid = ? , userid = ?, completed = ? , description = ? , createdat = ? where todoid = ?";
		try (Connection conn = connectionProvider.getConnection();
				PreparedStatement stmt = getPreparedStatementForSaveOrUpdateTodo(conn, query, todo, true)) {

			return stmt.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
		
	private PreparedStatement getPreparedStatementForSaveOrUpdateTodo(Connection conn, String query, Todo todo , boolean isUpdate) throws SQLException {
		PreparedStatement preparedstmt = conn.prepareStatement(query);
		preparedstmt.setString(1, todo.getTodoId().toString());
		preparedstmt.setString(2, todo.getUserId().toString());
		preparedstmt.setInt(3, todo.isCompleted()? 1:0);
		preparedstmt.setString(4, todo.getDescription());
		preparedstmt.setObject(5, Timestamp.from(todo.getCreatedAt()));
		
		if(isUpdate)
			preparedstmt.setString(6, todo.getTodoId().toString());
		return preparedstmt;
	}

	
	private Todo extractTodoFromResultSet(ResultSet result) throws SQLException {
		Todo todo = new Todo();
		todo.setCompleted(result.getBoolean("completed"));
		todo.setDescription(result.getString("description"));
		todo.setUserId(UUID.fromString(result.getString("userid")));
		todo.setTodoId(UUID.fromString(result.getString("todoid")));
		todo.setCreatedAt(getInstant(result,"createdat"));
		return todo;
	}
	
	private PreparedStatement getPreparedStatementForGetTodosByUser(Connection conn, UUID userId) throws SQLException {
		String query = "select * from todos where userid = ?";
		PreparedStatement preparedstmt = conn.prepareStatement(query);
		preparedstmt.setString(1, userId.toString());
		return preparedstmt;
	}
	private PreparedStatement getPreparedStatementForGetTodoById(Connection conn, UUID todoId) throws SQLException {
		String query = "select * from todos where todoid = ?";
		PreparedStatement preparedstmt = conn.prepareStatement(query);
		preparedstmt.setString(1, todoId.toString());
		return preparedstmt;
	}
	
	private Instant getInstant(ResultSet rs , String key) {
		try {
			Timestamp ts;
			ts =  rs.getObject(key,Timestamp.class);
			return ts.toInstant();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
		
	}


	
}
