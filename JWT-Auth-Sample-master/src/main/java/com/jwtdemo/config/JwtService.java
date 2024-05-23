package com.jwtdemo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET = "9a2f8c4e6b0d71f3e8b925a45747f894a3d6bc70fa8d5e21a15a6d8c3b9a0e7c";

    public String generateToken(UserDetails user){
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("authorities", populateAuthorities(user.getAuthorities()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+8640000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority:authorities){
            authoritiesSet.add(authority.getAuthority());
        }

        return String.join(",",authoritiesSet);
    }

    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parser().setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}
