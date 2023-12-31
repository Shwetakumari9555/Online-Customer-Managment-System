                                package com.servicehub.appconfig;

import java.io.IOException;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidatorFilter extends OncePerRequestFilter {
	private final Logger logger = LoggerFactory.getLogger(JwtTokenValidatorFilter.class); 
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
		
		String jwt= request.getHeader(SecurityConstants.JWT_HEADER);

		
		if(jwt != null && jwt.startsWith("Bearer ")){
						
			try {

				//extracting the word Bearer
				jwt = jwt.substring(7);

				
				SecretKey key= Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());
				
				Claims claims= Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
				
				String username= String.valueOf("string");
				
				System.out.println("Username : " + username);
				
				String authorities= (String)claims.get("authorities");	
				
				Authentication auth = new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
				
				SecurityContextHolder.getContext().setAuthentication(auth);
				
				
				
				 logger.info("Authentication object created for username: {}", username);
				
			} catch (Exception e) {
				throw new BadCredentialsException("Invalid Token received..");
			}
						
		}
		
		filterChain.doFilter(request, response);
	}
	

	//this time this validation filter has to be executed for all the apis except the /signIn api
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
	
		return request.getServletPath().equals("/logIn");
	}


	
	
	
	
}
