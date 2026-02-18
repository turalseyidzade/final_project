package azcompany.final_projeckt.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {
    private static final String ACCESS_TOKEN_SECRET = "626e9c4d248ba54c2127392506961426246513d54aed42a8d3c1fbc2";
    private static final String REFRESH_TOKEN = "8a44a27f5e86ce1a5ae88b527d7f7451de2d54261c2c21de8fdfe317";
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 15 * 60 * 1000;
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000;

    public String generateAccessToken(String username) {
        Key key = Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes());
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String username) {
        Key key = Keys.hmacShaKeyFor(REFRESH_TOKEN.getBytes());
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }
    public String extractUsernameFromAccessToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    public String extractUsernameFromRefreshToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(REFRESH_TOKEN.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}
