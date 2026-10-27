package com.example.ToDoService.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthorizationFilter extends OncePerRequestFilter {

    final private RestClient restClient = RestClient.builder()
            .baseUrl("http://localhose/8081/auth")
            .build();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String AuthHeader = request.getHeader("Authorization");
        if(AuthHeader == null || !AuthHeader.startsWith("Bearer")){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


        ResponseEntity<Object> validationResponse = restClient.post()
                .uri("/checkToken")
                .header("Authorization",AuthHeader)
                .retrieve()
                .toEntity(Object.class);

        if(validationResponse.getStatusCode().is2xxSuccessful()){
            filterChain.doFilter(request,response);
            return;
        }

         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
