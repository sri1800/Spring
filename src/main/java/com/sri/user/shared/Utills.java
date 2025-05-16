package com.sri.user.shared;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.sri.user.SpringApplicationContext;
import com.sri.user.security.AppProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class Utills {

	private final Random RANDOM=new SecureRandom();
	private final String ALPHABET="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	
	public String generateUserId(int length) {
		return generateRandomString(length);
	}
	
	private String generateRandomString(int length) {
		StringBuilder returnValue=new StringBuilder();
		for(int i=0;i<length;i++) {
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		return  returnValue.toString() ;
	}
	
//	before expiring jwt token instead of login again, popup is provided to user and after confirmation again jwt token is created 
	
	
//	public String generateToken(String userId) {
//		AppProperties appProperties=(AppProperties)SpringApplicationContext.getBean("AppProperties");
//		String commonKey=appProperties.getTokenSecret();
//		
//		SecretKey secretKey=Keys.hmacShaKeyFor(commonKey.getBytes());
//		
//		
//		String token=Jwts.builder()
//				.subject(userId)
//				.expiration(Date.from(Instant.now().plusMillis(Long.parseLong(appProperties.getTokenExpirationTime()))))
//				.issuedAt(Date.from(Instant.now()))
//				.signWith(secretKey)
//				.compact();
//		
//		return token;
//	}
//	
//	public static boolean hasTokenExpired(String token) {
//		boolean returnValue=false;
//		try {
//			AppProperties appProperties=(AppProperties)SpringApplicationContext.getBean("AppProperties");
//			String commonKey=appProperties.getTokenSecret();
//			
//			if(commonKey==null)return true;
//			
//			SecretKey key=Keys.hmacShaKeyFor(commonKey.getBytes(StandardCharsets.UTF_8));
//			
//			Claims claims=Jwts.parser()
//			.verifyWith(key)
//			.build()
//			.parseSignedClaims(token)
//			.getPayload();
//			
//			Date tokenExpiratDate=claims.getExpiration();
//			Date todayDate=new Date();
//			returnValue=tokenExpiratDate.before(todayDate);
//		} catch (ExpiredJwtException ex) {
//			returnValue=true;
//		}
//		return	returnValue ;
//	}
}
