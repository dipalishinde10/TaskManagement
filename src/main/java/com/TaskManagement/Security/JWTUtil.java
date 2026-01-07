package com.TaskManagement.Security;

import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.TaskManagement.Entity.User;
import com.TaskManagement.Enum.Permission;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

    private final Key key;
    private final long expirationTokenMS = 1000L * 60 * 60 * 24; // 24 hours

    public JWTUtil(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user) {

        Set<Permission> permissions =
                PermissionConfig.getRolePermission().get(user.getRole());

        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationTokenMS);

        return Jwts.builder()
                .setSubject(user.getUserEmail())
                .claim("role", user.getRole().name())
                .claim("permissions",
                        permissions.stream()
                                .map(Enum::name)
                                .collect(Collectors.toList()))
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
