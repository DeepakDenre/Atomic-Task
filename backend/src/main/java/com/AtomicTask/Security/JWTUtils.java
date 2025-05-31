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
	
	private final byte[] AccessJWTSecret;
	private final byte[] RefreshJWTSecret;
	private final Key accessKey;
	private final Key refreshKey;
	
	EnvConfig TC;
	
	public JWTUtils(EnvConfig TC) {
		this.TC = TC;
		AccessJWTSecret = TC.getAccessJWTSecret().getBytes();
		RefreshJWTSecret = TC.getRefreshJWTSecret().getBytes();
		accessKey = Keys.hmacShaKeyFor(AccessJWTSecret);
		refreshKey = Keys.hmacShaKeyFor(RefreshJWTSecret);
	}
	
	public String generateAccessToken(UserModel user) {
		Date now = new Date();
		Date AccessTokenExpiration = new Date(now.getTime() + TC.getAccessTokenExpiration());
		try {	
			return Jwts.builder()
					.setSubject(user.getUsername())
					.setIssuedAt(now)
					.setExpiration(AccessTokenExpiration)
					.signWith(accessKey, SignatureAlgorithm.HS256)
					.compact();
		}catch (Exception e) {
			return "";
		}
	}
	
	public String generateRefreshToken(UserModel user) {
		Date now = new Date();
		Date RefreshTokenExpiration = new Date(now.getTime() + TC.getRefreshTokenExpiration());
		try {
			return Jwts.builder()
					.setSubject(user.getUsername())
					.setIssuedAt(now)
					.setExpiration(RefreshTokenExpiration)
					.signWith(refreshKey, SignatureAlgorithm.HS256)
					.compact();
					
		}catch (Exception e) {
			return "";
		}
	}
	
	public String getUsernameFromAccessToken(String Token) {
		try {
			Jws<Claims> jws = Jwts.parserBuilder()
					.setSigningKey(Keys.hmacShaKeyFor(AccessJWTSecret))
					.setAllowedClockSkewSeconds(30000)
					.build()
					.parseClaimsJws(Token);
			return jws.getBody().getSubject();
		}catch (Exception e) {
			return "Error: "+e;
		}
	}

	
	public String getUsernameFromRefreshToken(String Token) {
		try {
			Jws<Claims> jws = Jwts.parserBuilder()
					.setSigningKey(Keys.hmacShaKeyFor(RefreshJWTSecret))
					.setAllowedClockSkewSeconds(30000)
					.build()
					.parseClaimsJws(Token);
			return jws.getBody().getSubject();
		}catch (Exception e) {
			return "Error: "+e;
		}
	}
	
	public boolean validateAccessToken(String Accesstoken) {
	    try {
	        Key key = Keys.hmacShaKeyFor(AccessJWTSecret); // JWTSecret must be a proper byte[] of 256-bit length
	        Jwts.parserBuilder()
	            .setSigningKey(key)
	            .setAllowedClockSkewSeconds(3000)
	            .build()
	            .parseClaimsJws(Accesstoken); // This line will throw if invalid

	        return true;
	    } catch (ExpiredJwtException e) {
	        System.out.println("AccessToken expired: " + e.getMessage());
	    } catch (UnsupportedJwtException e) {
	        System.out.println("Unsupported Access token: " + e.getMessage());
	    } catch (MalformedJwtException e) {
	        System.out.println("Malformed Access token: " + e.getMessage());
	    } catch (SecurityException e) {
	        System.out.println("Invalid Accesssignature: " + e.getMessage());
	    } catch (IllegalArgumentException e) {
	        System.out.println("Empty or null Access token");
	    }

	    return false;
	}
	public boolean validateRefreshToken(String Refreshtoken) {
	    try {
	        Key key = Keys.hmacShaKeyFor(RefreshJWTSecret); // JWTSecret must be a proper byte[] of 256-bit length
	        Jwts.parserBuilder()
	            .setSigningKey(key)
	            .setAllowedClockSkewSeconds(3000)
	            .build()
	            .parseClaimsJws(Refreshtoken); // This line will throw if invalid

	        return true;
	    } catch (ExpiredJwtException e) {
	        System.out.println("Refresh Token expired: " + e.getMessage());
	    } catch (UnsupportedJwtException e) {
	        System.out.println("Unsupported Refresh token: " + e.getMessage());
	    } catch (MalformedJwtException e) {
	        System.out.println("Malformed Refresh token: " + e.getMessage());
	    } catch (SecurityException e) {
	        System.out.println("Invalid Refresh signature: " + e.getMessage());
	    } catch (IllegalArgumentException e) {
	        System.out.println("Empty or null Refresh token");
	    }

	    return false;
	}
}
