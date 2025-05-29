package com.AtomicTask.Security;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.AtomicTask.Model.UserModel;
import com.AtomicTask.Repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
	@Autowired
	JWTUtils JwtUtils;
	
	@Autowired
	UserRepository userRepo;
	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException,IOException {
		try {
			String header = req.getHeader("Authorization");
			if( header != null && header.startsWith("Bearer ")) {
				String Token = header.substring(7);
				String username = JwtUtils.getUsernameFromToken(Token);
				if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserModel user = userRepo.findByUsername(username);
					if(JwtUtils.validateToken(Token)) {
						List<GrantedAuthority> authorities = Collections.singletonList(
							    new SimpleGrantedAuthority("ROLE_" + user.getRole()));
						UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user,null,authorities);
						auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
						SecurityContextHolder.getContext().setAuthentication(auth);
					}
				}
			}
			filterChain.doFilter(req, res);
		}catch (Exception e) {
			System.out.print("Error "+e);
			filterChain.doFilter(req, res);
		}
	}
}
