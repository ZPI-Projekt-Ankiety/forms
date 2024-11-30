package com.forms.formswebapp.user.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@Component
public class JwtService {

    public String generateToken(final String email, final Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(JwtUtilService.getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    public Boolean validateToken(final String token, final UserDetails userDetails) {
        final String userName = JwtUtilService.extractUserName(token);
        final boolean isTokenExpired = JwtUtilService.isTokenExpired(token);
        final boolean isUserNameEqual = userName.equals(userDetails.getUsername());
        return !isTokenExpired && isUserNameEqual;
    }

}