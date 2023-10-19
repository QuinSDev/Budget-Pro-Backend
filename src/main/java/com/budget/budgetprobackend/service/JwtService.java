package com.budget.budgetprobackend.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Servicio para generar tokens JWT utilizados en la autenticación y 
 * autorización de usuarios.
 * Este servicio proporciona métodos para crear tokens JWT con información del
 * usuario y reclamaciones adicionales.
 * 
 * @author QuinSDev
 */
@Service
public class JwtService {
    
    private static final String
            SECRET_KEY =  "GR3H51DE3SG15JK35KY3O5U1861GS3E48SH1R3543F3A1GHR1TJ";
    
    /**
     * Genera un token JWT para un usuario.
     * 
     * @param user: El UserDetails (detalles del usuario) para el cual se genera
     * el token.
     * @return El token generado.
     */
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }
    
    /**
     * Genera un token JWT con reclamaciones adicionales para un usuario.
     * 
     * @param extraClaims: Las reclamaciones adicionales que se agregarán al token.
     * @param user: El UserDetails (detalles del usuario) para el cual se genera
     * el token.
     * @return El token JWT generado con las reclamaciones específicas.
     */
    public String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Obtiene la clave secreta utilizada para firmar tokens JWT a partir de 
     * una clave en formato base64.
     * 
     * @return La clave secreta como un objeto Key.
     */
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}