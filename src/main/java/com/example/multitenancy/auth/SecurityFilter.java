package com.example.multitenancy.auth;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
/**
 * Don't do this , this is just for demonstration purposes use a proper Security
 * framework like Spring security for handlling jwts
 * 
 * i just prefer to build everything from scratch but i don't recommend it if
 * writing commercial software
 */
public class SecurityFilter extends OncePerRequestFilter {
	
	@Autowired private JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// disable cors
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		if(request.getMethod().equals("OPTIONS")) {
			filterChain.doFilter(request, response);
			return;
		}


		
		if(request.getRequestURI().startsWith("/auth")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		else if(hasValidToken(request)) {
			
			try {
				UserPrincipal principal = jwtTokenProvider.getUserPrincipalFromToken(resolveToken(request));
				UserPrincipalContextHolder.setContext(principal);
				filterChain.doFilter(request, response);
			} 
			finally {
				UserPrincipalContextHolder.clearContext();
			}
		}
		else {
			response.sendError(401,"Unauthorized");
		}		
	}

	private boolean hasValidToken(HttpServletRequest request) {
		var token = Optional.ofNullable(resolveToken(request));
		return (token.isPresent() && jwtTokenProvider.validateToken(token.get()));
	}

	private String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

}
