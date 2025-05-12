package com.sri.user.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.sri.user.SpringApplicationContext;
import com.sri.user.Entity.UserEntity;
import com.sri.user.repo.UserRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthorizationFilter extends BasicAuthenticationFilter{
	
	UserRepo userRepo;
	
	public AuthorizationFilter(AuthenticationManager authenticationManager,UserRepo userRepo) {
		super(authenticationManager);
		this.userRepo=userRepo;
	}
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException{
		String authorizationHeader=req.getHeader(SecurityConstants.HEADER_STRING);
		if(authorizationHeader==null || !authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}
		authorizationHeader=authorizationHeader.replace(SecurityConstants.TOKEN_PREFIX,"").trim();
		UsernamePasswordAuthenticationToken authorization=getAuthentication(authorizationHeader);
		SecurityContextHolder.getContext().setAuthentication(authorization);
		chain.doFilter(req, res);
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		AppProperties appProperties=(AppProperties)SpringApplicationContext.getBean("AppProperties");
		String commonKey=appProperties.getTokenSecret();
		
		if(commonKey==null)return null;
		
		SecretKey key=Keys.hmacShaKeyFor(commonKey.getBytes(StandardCharsets.UTF_8));
		
		Claims claims=Jwts.parser()
		.verifyWith(key)
		.build()
		.parseSignedClaims(token)
		.getPayload();
		
		
		
		String userId=claims.getSubject();
		
		if(userId!=null) {
			UserEntity entity=userRepo.findByUserId(userId);
			UserPrinciple userPrinciple=new UserPrinciple(entity);
			return new UsernamePasswordAuthenticationToken(userPrinciple,null,userPrinciple.getAuthorities());
		}
		return null;
	}
	

}
