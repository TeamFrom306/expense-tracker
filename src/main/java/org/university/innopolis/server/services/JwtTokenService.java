package org.university.innopolis.server.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@PropertySource({"classpath:jwt.properties"})
public class JwtTokenService implements TokenService {

    @Value("${keyword}")
    private String keyword;

    public String generateToken(String login, int id) {
        return Jwts.builder()
                .setSubject(login)
                .claim("id", id)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, keyword).compact();
    }

    @Override
    public int getAccountId(String token) {
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(keyword)
                    .parseClaimsJws(token)
                    .getBody();

            return claims.get("id", Integer.class);
        } catch (SignatureException e) {
            return -1;
        }
    }
}

