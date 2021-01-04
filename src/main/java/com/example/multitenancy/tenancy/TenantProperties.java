package com.example.multitenancy.tenancy;

import java.util.UUID;

import com.example.multitenancy.db.DatabaseProperties;

public class TenantProperties {
	
	private UUID tenantId;
	private String domain;
	
	private DatabaseProperties databaseProperties;
	
	
	public UUID getTenantId() {
		return tenantId;
	}
	public void setTenantId(UUID tenantId) {
		this.tenantId = tenantId;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public DatabaseProperties getDatabaseProperties() {
		return databaseProperties;
	}
	public void setDatabaseProperties(DatabaseProperties databaseProperties) {
		this.databaseProperties = databaseProperties;
	}
	
	
	
	
}
