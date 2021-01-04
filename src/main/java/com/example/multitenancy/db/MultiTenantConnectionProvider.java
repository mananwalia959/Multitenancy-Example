package com.example.multitenancy.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import com.example.multitenancy.auth.UserPrincipal;
import com.example.multitenancy.auth.UserPrincipalContextHolder;
import com.example.multitenancy.tenancy.TenantProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MultiTenantConnectionProvider {
	Map<UUID,DataSource> tenantIdToDataSources; 
	
	public MultiTenantConnectionProvider(List<TenantProperties> allTenantProperties) {
		this.tenantIdToDataSources = allTenantProperties.stream()
				.collect(Collectors.toMap(TenantProperties::getTenantId, this::DataSourcegetDataSource));
	}
	
	
	public DataSource DataSourcegetDataSource(TenantProperties properties) {
			var db = properties.getDatabaseProperties();
	        HikariConfig config = new HikariConfig();

	        config.setJdbcUrl(db.getConnectionString());
	        config.setUsername(db.getUsername());
	        config.setAutoCommit(true);
	        config.setMaximumPoolSize(5);
	        config.setPassword(db.getPassword());
	       

		return new HikariDataSource(config);
	    }

	public Connection getConnection(UUID tenantID) {
		try {
			return tenantIdToDataSources.get(tenantID).getConnection();
		} catch (SQLException e) { throw new RuntimeException(e); }
	}
	
	public Connection getConnection() {
		UserPrincipal principal = UserPrincipalContextHolder.getContext();
		return  getConnection(principal.getTenantId());
	}
	
	

}
