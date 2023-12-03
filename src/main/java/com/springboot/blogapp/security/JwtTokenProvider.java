package com.springboot.blogapp.security;

import com.springboot.blogapp.exception.BlogAPIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private long jwtExpirationDate;

    //Generate JWT token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(key())
                .compact();
        return token;
    }

    private SecretKey key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    //get username from JWT token
    public String getUsername(String token){
        Claims claims = Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String username = claims.getSubject();
        return username;
    }

    //validate jwt token
    public boolean validateToken(String token){
      try{
          Jwts.parser()
                  .verifyWith(key())
                  .build()
                  .parse(token);
          return true;
      }catch (MalformedJwtException ex){
          throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Invalid JWT token");
      }catch (ExpiredJwtException ex){
          throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Expired JWT token");
      }catch (UnsupportedJwtException ex){
          throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Unsupported JWT token");
      }catch (IllegalArgumentException ex){
          throw new BlogAPIException(HttpStatus.BAD_REQUEST," JWT claim string is empty");
      }
    }
}
