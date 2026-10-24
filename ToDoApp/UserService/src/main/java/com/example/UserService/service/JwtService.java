package com.example.UserService.service;

import com.example.UserService.entity.User;
import com.example.UserService.entity.Jwt;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

// create token, extract claims, validate token
@Service
public class JwtService {
    private final SecretKey secret_key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final JwtParser jwtParser;
    private final long tokenAccessTime = 15*60*1000;

    public JwtService() {
        this.jwtParser = Jwts.parser()  .verifyWith(secret_key)
                .build();
    }

    // create token
    String createToken(User user, Map<String,Object> claims){
         return Jwts.builder()
                .setSubject(user.getEmail())
                .setClaims(claims)
                .setSubject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+tokenAccessTime ))
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact();

    }

    // resolve token
    public String resolveToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if(authHeader !=null && authHeader.startsWith("Bearer")){
            return authHeader.substring(7);
        }
        return null;
    }

    // resolve claims
    public Claims resolveClaims(HttpServletRequest request){
        try{
            String accessToken = resolveToken(request);
            if(accessToken != null){
                return jwtParser.parseSignedClaims(accessToken).getPayload();
            }
            return null;
        }catch (ExpiredJwtException ex){
            request.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception e) {
            request.setAttribute("invalid", e.getMessage());
            throw e;
        }
    }

    // validate token
    boolean isTokenExpired(String token){
        try {
            Date expire =jwtParser.parseSignedClaims(token).getPayload().getExpiration();
            if(expire.before(new Date(System.currentTimeMillis()))){
                return false;
            }
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean isValidToken(String token, UserDetails user){
        // we make this comparison only for that line
        // as when we call jwtParser.parse here he first check signature of token then return claims
        // so reason for that comparison is to check signature & notExpired

        Claims claims = jwtParser.parseSignedClaims(token).getPayload();
        String subject = claims.getSubject();
        String username = user.getUsername();

        return (subject.equals(username) && !isTokenExpired(token));
    }

}
