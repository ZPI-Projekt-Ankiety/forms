package com.forms.formswebapp.user.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
class JwtUtilService {

    @Value("${com.forms.signingKey}")
    private String signingKey;


    private static String SIGNING_KEY;

    @PostConstruct
    public void init() {
        SIGNING_KEY = signingKey;
    }


    static String extractUserName(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    static Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    static Claims extractAllClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build().parseClaimsJws(token).getBody();
    }

    static Boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    static <T> T extractClaim(final String token, final Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    static Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SIGNING_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
