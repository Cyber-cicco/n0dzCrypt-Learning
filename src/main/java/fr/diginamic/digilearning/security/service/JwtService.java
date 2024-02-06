package fr.diginamic.digilearning.security.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.digilearning.entities.Role;
import fr.diginamic.digilearning.entities.Utilisateur;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Classe contenant les méthodes de création et d'extraction d'informatinos du JWT
 * */
@Configuration
@Data
@Service
@Slf4j
public class JwtService {
    @Value("${jwt.expires_in}")
    private long expireIn;

    @Value("${jwt.cookie}")
    private String cookie;

    @Value("${jwt.secret}")
    private String secret;

    private Key secretKey;

    /**
     * Permet de créer la clé secrète du JWT
     */
    @PostConstruct
    public void buildKey() {
        secretKey = new SecretKeySpec(Base64.getDecoder().decode(getSecret()),
                SignatureAlgorithm.HS256.getJcaName());
    }


    /**
     * Permet de créer le JWT à partir des informations de l'utilisateur
     * @param user l'utilisateur
     * @return le JWT sous forme de chaine de caractères
     */
    public String buildJWTCookie(Utilisateur user) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jetonJWT = null;
        try {
            jetonJWT = Jwts.builder()
                    .setSubject(user.getEmail())
                    .addClaims(Map.of("role",  objectMapper.writeValueAsString(user.getRoles().stream()
                            .map(Role::getLibelle)
                            .toList())
                    ))
                    .addClaims(Map.of("id", user.getId()))
                    .addClaims(Map.of("banned", user.getBanned()))
                    .setExpiration(new Date(System.currentTimeMillis() + getExpireIn() * 1000))
                    .signWith(
                            getSecretKey()
                    ).compact();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ResponseCookie tokenCookie = ResponseCookie.from(getCookie(), jetonJWT)
                .httpOnly(false)
                .maxAge(getExpireIn() * 1000)
                .path("/")
                .sameSite("Lax")
                .build();

        return tokenCookie.toString();
    }

    /**
     * permet de récupérer l'email dans le JWT
     * @param token le jwt
     * @return l'email
     */
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractEmail(Claims claims) {
        return claims.getSubject();
    }

    /**
     * Permet de récupérer le role stocké dans le jidoublevété
     * @param token le jwt
     * @return le role sous forme de chaine de caractère
     */
    public List<String> extractRoles(String token){
        Claims tokenClaims = extractAllClaims(token);
        return extractRoles(tokenClaims);
    }

    public List<String> extractRoles(Claims claims){
        ObjectMapper objectMapper = new ObjectMapper();
        String rolesAsJson = claims.get("role", String.class);
        try {
            return objectMapper.readValue(rolesAsJson, List.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Permet de récupéerr l'identifiant stocké dans le JWT
     * @param token le jwt
     * @return l'id
     */
    public Long extractId(String token){
        Claims tokenClaims = extractAllClaims(token);
        return tokenClaims.get("id", Long.class);
    }

    public Long extractId(Claims claims){
        return claims.get("id", Long.class);
    }

    /**
     * Permet de récupérer une partie du JWT
     * @param token le gidoubleuvaytay
     * @param claimsResolver la fonction à appliquer pour récupérer la claim du jwt
     * @return la claim
     * @param <T> le type de la claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Permet de récupérer toutes les claims du JWT
     * @param token le jydoubleuvétaient
     * @return toutes les claims
     */
    public Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean extractBanned(Claims claims) {
        return claims.get("banned", Boolean.class);
    }
}
