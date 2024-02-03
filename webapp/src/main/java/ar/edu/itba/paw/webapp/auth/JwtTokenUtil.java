package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

//https://github.com/Yoh0xFF/java-spring-security-example

@Component
public class JwtTokenUtil {

    private static final int EXPIRY_TIME = 29 * 1000; //1 day (in millis)
    private static final int REFRESH_EXPIRY_TIME = 30 * 1000; //1 week (in millis)


    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private Key jwtKey;

    public JwtTokenUtil() {
    }

    public UserDetails parseToken(String jws) {
        try {
            final Claims claims = Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(jws).getBody();

            if (new Date(System.currentTimeMillis()).after(claims.getExpiration())) {
                return null;
            }

            final String username = claims.getSubject();

            return userDetailsService.loadUserByUsername(username);
        } catch (Exception e) {
            return null;
        }
    }

    public String createToken(User user, String baseUrl) {
        Claims claims = Jwts.claims();

        claims.setSubject(user.getCuit());
        claims.put("userURL", baseUrl + "/users/" + user.getUserId());
        claims.put("authorization", user.getRole());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRY_TIME))
                .signWith(jwtKey)
                .compact();
    }

    public String createRefreshToken(User user) {
        Claims claims = Jwts.claims();

        claims.setSubject(user.getCuit());
        claims.put("refresh", true);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRY_TIME))
                .signWith(jwtKey)
                .compact();
    }

    public boolean isRefreshToken(String token){
        try {
            final Claims claims = Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token).getBody();
            return claims.get("refresh", Boolean.class);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateAccessToken(String token) {
        final Claims claims = Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token).getBody();
        return claims.get("refresh", Boolean.class) != null && !(new Date(System.currentTimeMillis()).after(claims.getExpiration()));
    }
}