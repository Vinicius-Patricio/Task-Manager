package task_manager.portifolio.vinicius.security;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

//Manipulação de tokens JWT em aplicações Spring Boot

@Component
public class JwtTokenUtil {
    
    //Chave secreta para assinar os tokens
    @Value("${JWT_SECRET_KEY}")
    private String jwtSecretKey;

    //Chave secreta convertida em formato de JWT
    private SecretKey secretKey;

    //Validaçao da chave e criaçao da chave
    @PostConstruct
    public void init() {
        String cleanedKey = jwtSecretKey.replaceAll("[^a-zA-Z0-9+/=]", "").trim();
        byte[] keyBytes = Decoders.BASE64.decode(cleanedKey);
        
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("Chave deve ter pelo menos 256 bits (32 bytes)");
        }
        
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    //
    public String generateToken(String username) {

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username não pode ser nulo/vazio");
        }

            String token = Jwts.builder()
            .subject(username) // Usuário
            .issuedAt(new Date())  // Data de emissão
            .expiration(new Date(System.currentTimeMillis() + 43200000)) // Tempo de expiração = 12hrs
            .signWith(secretKey) //Assina com a chave secreta
            .compact(); //Transforma em String
    
        return token;
    }


    //Extrai o nome de usuário do token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    //Verifica se o token é válido
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    //Verifica se o token se expirou
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //Extrai a data de expiração
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //Extrai Claims expecificas(Claims são informações contidas no payload de um token)
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //Extrai todas as Claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
