package com.example.multitenancy.auth;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	//please don't be inspired by this , hardcoding secrets in code is really really bad;
	private String secretKey = "tqnbXHsJypDiUgrK9KkbphjDDEXP7hq1";
	
	private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512; 
	
	//oof
	private int validity = Integer.MAX_VALUE;
	
	private String tenantIdIdentifier = "TENANT_ID";
	
	
	
	public String signToken(UUID userID, UUID tenantIDentifier) {
		Claims claims = Jwts.claims().setSubject(userID.toString());
		claims.put(tenantIdIdentifier, tenantIDentifier.toString());

		return Jwts.builder().setClaims(claims).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + validity))
				.signWith(signatureAlgorithm, secretKey)
				.compact();
	}

	public UserPrincipal getUserPrincipalFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		var userID = UUID.fromString(claims.getSubject());
		var tenantId = UUID.fromString(claims.get(tenantIdIdentifier,String.class));
		return new UserPrincipal(userID, tenantId);
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
		} 
		return false;
	}

	
}
