package com.pinapp.challenge.entrypoint.web.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  private String secret;

  public String generateToken(String username) {
    Date now = new Date();
    Date expiry = new Date(now.getTime() + Duration.ofMinutes(15).toMillis());

    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(now)
        .setExpiration(expiry)
        .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
        .compact();
  }

  public Jws<Claims> parseToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
        .build()
        .parseClaimsJws(token);
  }

  public String getUsername(String token) {
    return parseToken(token).getBody().getSubject();
  }
}
