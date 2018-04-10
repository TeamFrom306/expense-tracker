package org.university.innopolis.server.services.helpers;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.university.innopolis.server.services.exceptions.CorruptedTokenException;
import org.university.innopolis.server.services.exceptions.ExpiredTokenException;

import java.util.Date;

@Service
@PropertySource({"classpath:jwt.properties"})
public class JwtTokenService implements TokenService {

    @Value("${keyword}")
    private String keyword;

    @Value("${expiration-time}")
    private int expTime;

    public String generateToken(String login, int id) {
        Date expDate = new Date(new Date().getTime() + expTime * 1000);


        return Jwts.builder()
                .setSubject(login)
                .claim("id", id)
                .setIssuedAt(new Date())
                .setExpiration(expDate)
                .signWith(SignatureAlgorithm.HS256, keyword).compact();
    }

    @Override
    public void validateToken(String token) throws
            ExpiredTokenException,
            CorruptedTokenException {
        try {
            Jwts.parser()
                    .setSigningKey(keyword)
                    .parseClaimsJws(token);
        } catch (SignatureException | 
                MalformedJwtException |
                UnsupportedJwtException e) {
            throw new CorruptedTokenException();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException(e.getMessage());
        }
    }
}

