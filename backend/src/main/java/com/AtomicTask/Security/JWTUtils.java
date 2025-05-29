package com.AtomicTask.Security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.AtomicTask.Config.EnvConfig;
import com.AtomicTask.Model.UserModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtils {
	
	private final byte[] JWTSecret;
	private final Date TokenExpiration;
	private final Date now;
	
	public JWTUtils(EnvConfig TC) {
		now = new Date();
		JWTSecret = TC.getJWTSecret().getBytes();
		TokenExpiration = new Date(now.getTime() + TC.getTokenExpiration());
	}
	
	public String generateToken(UserModel user) {
		try {
			return Jwts.builder()
					.setSubject(user.getUsername())
					.setIssuedAt(now)
					.setExpiration(TokenExpiration)
					.signWith(SignatureAlgorithm.HS256, JWTSecret)
					.compact();
		}catch (Exception e) {
			return "";
		}
	}
	
	public String getUsernameFromToken(String Token) {
		try {
			Jws<Claims> jws = Jwts.parserBuilder()
					.setSigningKey(Keys.hmacShaKeyFor(JWTSecret))
					.setAllowedClockSkewSeconds(30000)
					.build()
					.parseClaimsJws(Token);
			return jws.getBody().getSubject();
		}catch (Exception e) {
			return "Error: "+e;
		}
	}
	
	public boolean validateToken(String token) {
	    try {
	        Key key = Keys.hmacShaKeyFor(JWTSecret); // JWTSecret must be a proper byte[] of 256-bit length
	        Jwts.parserBuilder()
	            .setSigningKey(key)
	            .setAllowedClockSkewSeconds(3000)
	            .build()
	            .parseClaimsJws(token); // This line will throw if invalid

	        return true;
	    } catch (ExpiredJwtException e) {
	        System.out.println("Token expired: " + e.getMessage());
	    } catch (UnsupportedJwtException e) {
	        System.out.println("Unsupported token: " + e.getMessage());
	    } catch (MalformedJwtException e) {
	        System.out.println("Malformed token: " + e.getMessage());
	    } catch (SecurityException e) {
	        System.out.println("Invalid signature: " + e.getMessage());
	    } catch (IllegalArgumentException e) {
	        System.out.println("Empty or null token");
	    }

	    return false;
	}
}
