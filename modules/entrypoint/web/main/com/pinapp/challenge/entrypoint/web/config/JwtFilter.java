package com.pinapp.challenge.entrypoint.web.config;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(1)
public class JwtFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  public JwtFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String path = request.getRequestURI();
    if ("/login".equals(path) || "/v3/api-docs.yaml".equals(path)) {
      filterChain.doFilter(request, response);
      return;
    }

    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      return;
    }

    String token = authHeader.substring(7);
    try {
      jwtService.parseToken(token);
      filterChain.doFilter(request, response);
    } catch (JwtException e) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
  }
}
