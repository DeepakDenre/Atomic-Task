package com.AtomicTask.Utlity;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

import org.springframework.http.ResponseCookie;
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
import io.jsonwebtoken.security.SignatureException;

@Component
public class JWTUtils {
	
	private final byte[] AccessJWTSecret;
	private final byte[] RefreshJWTSecret;
	private final Key accessKey;
	private final Key refreshKey;
	
	private final EnvConfig TC;
	
	public JWTUtils(EnvConfig TC) {
		this.TC = TC;
		this.AccessJWTSecret = TC.getAccessJWTSecret().getBytes();
		this.RefreshJWTSecret = TC.getRefreshJWTSecret().getBytes();
		this.accessKey = Keys.hmacShaKeyFor(AccessJWTSecret);
		this.refreshKey = Keys.hmacShaKeyFor(RefreshJWTSecret);
	}
	
	public ResponseCookie generateAccessToken(UserModel user) {
		Date now = new Date();
		Date accessTokenExpiration = new Date(now.getTime() + TC.getAccessTokenExpiration());
		
		String token = Jwts.builder()
				.setSubject(user.getUsername())
				.setIssuedAt(now)
				.setExpiration(accessTokenExpiration)
				.setIssuer("AtomicTask")
				.setAudience("AtomicTask-Client")
				.setId(UUID.randomUUID().toString())
				.signWith(accessKey, SignatureAlgorithm.HS256)
				.compact();
		
		ResponseCookie cookie = ResponseCookie.from("accessToken", token)
				.httpOnly(true)
				.secure(false)
				.path("/")
				.maxAge(Duration.ofMillis(TC.getAccessTokenExpiration()))
				.build();
		return cookie;
	}
	
	public ResponseCookie generateRefreshToken(UserModel user) {
		Date now = new Date();
		Date refreshTokenExpiration = new Date(now.getTime() + TC.getRefreshTokenExpiration());
		
		String token =  Jwts.builder()
				.setSubject(user.getUsername())
				.setIssuedAt(now)
				.setExpiration(refreshTokenExpiration)
				.setIssuer("AtomicTask")
				.setAudience("AtomicTask-Client")
				.setId(UUID.randomUUID().toString())
				.signWith(refreshKey, SignatureAlgorithm.HS256)
				.compact();

		ResponseCookie cookie = ResponseCookie.from("refreshToken", token)
				.httpOnly(true)
				.secure(false)
				.path("/")
				.maxAge(Duration.ofMillis(TC.getRefreshTokenExpiration()))
				.build();
		return cookie;
	}
	
	public String getUsernameFromAccessToken(String token) {
		try {
			Jws<Claims> jws = Jwts.parserBuilder()
					.setSigningKey(accessKey)
					.setAllowedClockSkewSeconds(3000)
					.build()
					.parseClaimsJws(token);
			return jws.getBody().getSubject();
		} catch (ExpiredJwtException e) {
			throw new JwtAuthenticationException("Access token has expired");
		} catch (UnsupportedJwtException e) {
			throw new JwtAuthenticationException("Unsupported JWT token");
		} catch (MalformedJwtException e) {
			throw new JwtAuthenticationException("Invalid JWT token");
		} catch (SignatureException e) {
			throw new JwtAuthenticationException("Invalid JWT signature");
		} catch (IllegalArgumentException e) {
			throw new JwtAuthenticationException("JWT token is empty or null");
		}
	}
	
	public String getUsernameFromRefreshToken(String token) {
		try {
			Jws<Claims> jws = Jwts.parserBuilder()
					.setSigningKey(refreshKey)
					.setAllowedClockSkewSeconds(3000)
					.build()
					.parseClaimsJws(token);
			return jws.getBody().getSubject();
		} catch (ExpiredJwtException e) {
			throw new JwtAuthenticationException("Refresh token has expired");
		} catch (UnsupportedJwtException e) {
			throw new JwtAuthenticationException("Unsupported JWT token");
		} catch (MalformedJwtException e) {
			throw new JwtAuthenticationException("Invalid JWT token");
		} catch (SignatureException e) {
			throw new JwtAuthenticationException("Invalid JWT signature");
		} catch (IllegalArgumentException e) {
			throw new JwtAuthenticationException("JWT token is empty or null");
		}
	}
	
	public boolean validateAccessToken(String accessToken) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(accessKey)
				.setAllowedClockSkewSeconds(3000)
				.build()
				.parseClaimsJws(accessToken);
			return true;
		} catch (ExpiredJwtException e) {
			throw new JwtAuthenticationException("Access token has expired");
		} catch (UnsupportedJwtException e) {
			throw new JwtAuthenticationException("Unsupported JWT token");
		} catch (MalformedJwtException e) {
			throw new JwtAuthenticationException("Invalid JWT token");
		} catch (SignatureException e) {
			throw new JwtAuthenticationException("Invalid JWT signature");
		} catch (IllegalArgumentException e) {
			throw new JwtAuthenticationException("JWT token is empty or null");
		}
	}
	
	public boolean validateRefreshToken(String refreshToken) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(refreshKey)
				.setAllowedClockSkewSeconds(3000)
				.build()
				.parseClaimsJws(refreshToken);
			return true;
		} catch (ExpiredJwtException e) {
			throw new JwtAuthenticationException("Refresh token has expired");
		} catch (UnsupportedJwtException e) {
			throw new JwtAuthenticationException("Unsupported JWT token");
		} catch (MalformedJwtException e) {
			throw new JwtAuthenticationException("Invalid JWT token");
		} catch (SignatureException e) {
			throw new JwtAuthenticationException("Invalid JWT signature");
		} catch (IllegalArgumentException e) {
			throw new JwtAuthenticationException("JWT token is empty or null");
		}
	}
}

final class JwtAuthenticationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public JwtAuthenticationException(String message) {
		super(message);
	}
}
