package it.epicode.pugnatorisClub.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.epicode.pugnatorisClub.exception.UnAuthorizedException;
import it.epicode.pugnatorisClub.model.Utente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@PropertySource("application.properties")
public class JwtTools {
    @Value("${spring.jwt.secret}")
    private String secret;
    @Value("${spring.jwt.expirationMs}")
    private String expirationMs;

    public String createToken(Utente user){
        return Jwts.builder().subject(user.getUsername()).issuedAt(new Date(System.currentTimeMillis())).
                expiration(new Date(System.currentTimeMillis()+Long.parseLong(expirationMs))).
                signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
    }

    public void validateToken(String token){
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
    }

    public String extractUsernameFromToken(String token){
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parseSignedClaims(token).
                getPayload().getSubject();
    }
}
