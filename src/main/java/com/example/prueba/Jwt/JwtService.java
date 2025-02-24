package com.example.prueba.Jwt;

import org.springframework.stereotype.Service;
import com.example.prueba.User.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY="95AB593FC5D1881DEAB4CA93143FF83A12783A5D224DADE4984BE8F962";

    public String getToken(UserDetails user){
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String,Object> extraClaims, UserDetails user){

        if (user instanceof User) {
            User usuario = (User) user;
            extraClaims.put("documento", usuario.getDocumento());
            extraClaims.put("email", usuario.getEmail());
        }

        extraClaims.put("role", user.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList())); 

        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+1000*60*60)) // 1 hora
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
            
    }

    private Key getKey() {
        byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        //return getClaim(token, Claims::getSubject);
        return getClaim(token, claims -> claims.get("email", String.class));
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = getUsernameFromToken(token);
        if (userDetails instanceof User) {
            User user = (User) userDetails;
            return (email.equals(user.getEmail()) && !isTokenExpired(token));
        }
        return false;
    }

    private Claims getAllClaims(String token){
        return Jwts
            .parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }

    public String getDocumentoFromToken(String token) {
        Integer documento = getClaim(token, claims -> claims.get("documento", Integer.class));
        return String.valueOf(documento);
    }
}
