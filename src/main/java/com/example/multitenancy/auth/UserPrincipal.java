package com.example.multitenancy.auth;

import java.util.UUID;

public class UserPrincipal {
	
	private UUID userID;
	private UUID tenantId;
	
	public UserPrincipal(UUID userID, UUID tenantId) {
		super();
		this.userID = userID;
		this.tenantId = tenantId;
	}

	public UUID getUserID() {
		return userID;
	}

	public UUID getTenantId() {
		return tenantId;
	}
}
