package com.example.UserService.auth;

import com.example.UserService.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;

    @Autowired
    UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // check if there is token in header or not
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("bearer")){
            filterChain.doFilter(request,response);
            return;
        }

        // authenticate user if it not authenticated + validateToken
        Claims claims = jwtService.resolveClaims(request);
        String userEmail = claims.getSubject();
        String token = jwtService.resolveToken(request);
        if(SecurityContextHolder.getContext().getAuthentication() == null && userEmail != null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            if(userDetails!=null && jwtService.isValidToken(token,userDetails)) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // go to next filter
        filterChain.doFilter(request,response);
    }




}
