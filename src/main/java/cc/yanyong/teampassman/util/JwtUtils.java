package cc.yanyong.teampassman.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS512.key().build();
    public static final long EXPIRATION_TIME_MS = 86400000L;  // 24小时

    private JwtUtils() {}

    public static String generateToken(String subject, Map<String, ?> claims) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME_MS);

        return Jwts.builder()
                .header().add("alg", "HS512").add("typ", "JWT")
                .and()
                .subject(subject)
                .claims(claims)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(SECRET_KEY, Jwts.SIG.HS512)
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token.replace("Bearer ", ""))
                .getPayload();
    }

    public static Object getClaimFromToken(String token, String claimKey) {
        Claims claims = parseToken(token);
        return claims.get(claimKey);
    }

    public static Date getExpirationDateFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getExpiration();
    }

    public static long getExpirationTimeFromToken(String token) {
        return getExpirationDateFromToken(token).getTime();
    }

    public static boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
