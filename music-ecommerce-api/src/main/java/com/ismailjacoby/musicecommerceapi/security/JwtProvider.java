package com.ismailjacoby.musicecommerceapi.security;
import com.ismailjacoby.musicecommerceapi.exceptions.InvalidTokenException;
import com.ismailjacoby.musicecommerceapi.models.entities.User;
import com.ismailjacoby.musicecommerceapi.models.enums.UserRole;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.auth0.jwt.JWT;
import java.time.Instant;

@Component
public class JwtProvider {
    private static final String JWT_SECRET = "51bJYq99MpUVBcpeUIZ9IatDBT5rHfA985vBDybBtdSSkq1ZiQ";
    private static final long EXPIRES_AT = 900_000;
    private static final String AUTH_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final UserDetailsService userDetailsService;

    public JwtProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Generate Token
    public String generateToken(String username, UserRole role) {
        return TOKEN_PREFIX + JWT.create()
                .withSubject(username)
                .withClaim("role", role.toString())
                .withExpiresAt(Instant.now().plusMillis(EXPIRES_AT))
                .sign(Algorithm.HMAC512(JWT_SECRET));
    }

    // Extract Token
    public String extractToken(HttpServletRequest request) {
        String header = request.getHeader(AUTH_HEADER);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            return null;
        }

        return header.replaceFirst(TOKEN_PREFIX, "");
    }

    // Validate Token
    public boolean validateToken(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC512(JWT_SECRET))
                    .acceptExpiresAt(EXPIRES_AT)
                    .withClaimPresence("sub")
                    .withClaimPresence("role")
                    .build()
                    .verify(token);

            String username = jwt.getSubject();
            User user = (User) userDetailsService.loadUserByUsername(username);
            if (!user.isEnabled()) {
                return false;
            }

            UserRole tokenRole = UserRole.valueOf(jwt.getClaim("role").asString());

            return user.getRole() == tokenRole;
        } catch (JWTVerificationException | UsernameNotFoundException ex) {
            return false;
        }
    }

    // Create Authentication
    public Authentication createAuthentication(String token) {
        DecodedJWT jwt = JWT.decode(token);
        String username = jwt.getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), null, userDetails.getAuthorities()
        );
    }

    // Generate Reset Token
    public String generateResetToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(Instant.now().plusSeconds(15 * 60)) // 15 minutes
                .sign(Algorithm.HMAC512(JWT_SECRET));
    }

    // Validate Reset Token
    public String validateResetToken(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC512(JWT_SECRET))
                    .acceptExpiresAt(900) // 15 mins
                    .build()
                    .verify(token);
            return jwt.getSubject(); // Email
        } catch (JWTVerificationException ex) {
            throw new InvalidTokenException("Invalid or expired reset token.");
        }
    }
}
