package ru.netology.cloudStorage.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.netology.cloudStorage.entity.user.User;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtProvider {

    private final SecretKey jwtAccessSecret;
    private User authUser;
    @Value("${jwt.time-life-token}")
    private long timeLifeToken;

    private final Set<String> validToken;

    public JwtProvider(
            @Value("${jwt.secret.access}") String jwtAccessSecret) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.validToken = new HashSet<>();
    }

    public User getAuthorizedUser() {
        return authUser;
    }

    public String generateAccessToken(@NonNull User user) {
        authUser = user;
        LocalDateTime now = LocalDateTime.now();
        Instant accessExpirationInstant = now.plusMinutes(timeLifeToken)
                .atZone(ZoneId.systemDefault()).toInstant();
        Date accessExpiration = Date.from(accessExpirationInstant);
        String token = Jwts.builder()
                .setId(String.valueOf(user.getId()))
                .setSubject(user.getLogin())
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim("roles", user.getRoles())
                .compact();
        validToken.add(token);
        return token;
    }

    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        if (!validToken.contains(token)) {
            return false;
        }
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {

        } catch (UnsupportedJwtException unsEx) {

        } catch (MalformedJwtException mjEx) {

        } catch (SignatureException sEx) {

        } catch (Exception e) {

        }
        return false;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }

    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public void removeToken(String token) {
        validToken.remove(token);
    }
}
