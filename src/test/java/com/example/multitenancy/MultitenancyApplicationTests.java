package com.example.multitenancy;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.example.multitenancy.auth.JwtTokenProvider;


class MultitenancyApplicationTests {
	
	JwtTokenProvider provider = new JwtTokenProvider();

	@Test
	void testProvider() {
		
		UUID userID = UUID.randomUUID();
		UUID random = UUID.randomUUID();
		
		
		String token = provider.signToken(userID, random );
		
		var prp = provider.getUserPrincipalFromToken(token);
		System.out.println(token);
		
		System.out.println(prp.getTenantId() + " || " + prp.getUserID());
	}

}
