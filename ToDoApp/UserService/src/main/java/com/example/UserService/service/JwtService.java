package com.example.UserService.service;

import com.example.UserService.entity.User;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

// create token, extract claims, validate token
@Service
public class JwtService {
    private final String secret_key = "ASDFGHJKLQW";
    private final JwtParser jwtParser;
    private final long tokenAccessTime = 15*60*1000;

    public JwtService() {
        this.jwtParser = Jwts.parser().build();
    }

    // create token
    String createToken(User user, Map<String,Object> claims){
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setClaims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+tokenAccessTime ))
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact();
    }

    // resolve token
    String resolveToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if(authHeader !=null && authHeader.startsWith("bearer")){
            return authHeader.substring(7);
        }
        return null;
    }

    // resolve claims
    Claims resolveClaims(HttpServletRequest request){
        try{
            String accessToken = resolveToken(request);
            if(accessToken != null){
                return jwtParser.parseClaimsJwt(accessToken).getBody();
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
            Date expire = jwtParser.parseClaimsJwt(token).getBody().getExpiration();
            if(expire.before(new Date(System.currentTimeMillis()))){
                return false;
            }
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    boolean isValidToken(String token, UserDetails user){
        // we make this comparison only for that line
        // as when we call jwtParser.parse here he first check signature of token then return claims
        // so reason for that comparison is to check signature & notExpired

        Claims claims = jwtParser.parseClaimsJwt(token).getBody();
        String subject = claims.getSubject();
        String username = user.getUsername();

        return (subject.equals(username) && !isTokenExpired(token));
    }

}
