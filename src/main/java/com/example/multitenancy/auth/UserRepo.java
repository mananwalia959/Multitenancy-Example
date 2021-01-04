package com.example.multitenancy.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.example.multitenancy.db.MultiTenantConnectionProvider;

@Component
public class UserRepo {
	@Autowired private MultiTenantConnectionProvider connectionProvider;

	public User findUserByUserName(String userName, UUID tenantId) {
		String query = "select * from users where username = ?";
		try (Connection conn = connectionProvider.getConnection(tenantId);
				PreparedStatement stmt = getPreparedStatementForGetUser(query, conn,userName) ;
				ResultSet result = stmt.executeQuery()) {
			
			
			if (result.next()) {
				User user = new User();
				user.setUsername(result.getString("username"));
				user.setUserId(UUID.fromString(result.getString("userid")));
				/**
				 * lol never store passwords directly , use an encryption lib like bcrypt
				 * This is just for demonstration
				 */
				user.setPassword(result.getString("password"));
				return user;
			} else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No User Found");
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	private PreparedStatement getPreparedStatementForGetUser(String query, Connection conn, String userName) throws SQLException {
		 PreparedStatement preparedstmt = conn.prepareStatement(query);
		 preparedstmt.setString(1, userName);
		 return preparedstmt;
	}

}
