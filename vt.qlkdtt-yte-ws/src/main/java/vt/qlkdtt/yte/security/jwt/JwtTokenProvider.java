package vt.qlkdtt.yte.security.jwt;


import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vt.qlkdtt.yte.common.DataUtil;
import vt.qlkdtt.yte.security.user.CustomUserDetails;
import vt.qlkdtt.yte.service.exception.InvalidTokenRequestException;

import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtTokenProvider {
    private final String JWT_SECRET = "lodaaaaaa";
    private final long JWT_EXPIRATION = 1;

    public String generateToken(CustomUserDetails userDetails) {
        // Lấy thông tin user
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getUser().getUserId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
    }

//    public Boolean validateToken(String token) {
//        final String username = getUsernameFromToken(token);
//        return username != null && !isTokenExpired(token);
//    }

    public boolean validateToken(String authToken) {
        boolean isTokenVerified = false;
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            isTokenVerified = true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw new InvalidTokenRequestException("JWT", authToken, "Incorrect signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw new InvalidTokenRequestException("JWT", authToken, "Malformed jwt token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw new InvalidTokenRequestException("JWT", authToken, "Token expired. Refresh required");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw new InvalidTokenRequestException("JWT", authToken, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw new InvalidTokenRequestException("JWT", authToken, "Illegal argument token");
        } finally {
            return isTokenVerified;
        }
    }
}
