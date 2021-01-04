package com.example.multitenancy.auth;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.multitenancy.tenancy.TenantInformationProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	
	@Autowired private UserRepo userRepo;
	@Autowired private JwtTokenProvider tokenProvider;
	@Autowired private TenantInformationProvider tenantInfoProvider;
	
	private RuntimeException invalidLoginAttempt = new ResponseStatusException(HttpStatus.FORBIDDEN , "invalid login attempt");

	@PostMapping(path = "/login")
	public LoginResponse login( @RequestBody LoginRequest loginRequest) {
		
		if(isEmptyString(loginRequest.getDomain()))
			throw invalidLoginAttempt;
		
		if(isEmptyString(loginRequest.getUsername()) ||  isEmptyString(loginRequest.getPassword()))
			throw invalidLoginAttempt; 
		
		String domain = loginRequest.getDomain();
		UUID tenantId = tenantInfoProvider.getTenantPropertyByDomain(domain).getTenantId();
		User user = userRepo.findUserByUserName(loginRequest.getUsername(),tenantId);
		/**
		 * lol never store passwords directly , use an encryption lib like bcrypt
		 * This is just for demonstration
		 */
		if(!user.getPassword().equals(loginRequest.getPassword())) {
			throw invalidLoginAttempt;
		}
		
		String token = tokenProvider.signToken(user.getUserId(), tenantId);
		return new LoginResponse(token);
	}
	
	private boolean isEmptyString(String str) {
		return Objects.isNull(str) || str.isBlank();
	}

	
	
	
}
