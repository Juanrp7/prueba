package com.example.prueba.Jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        final String token = getTokenFromRequest(request);
        final String username;

        if (token == null) {
            System.out.println("No se encontró token en la solicitud.");
            filterChain.doFilter(request, response);
            return;   
        }

        username = jwtService.getUsernameFromToken(token);
        System.out.println("Usuario extraído del token: " + username);

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            try {
                UserDetails userDetails=userDetailsService.loadUserByUsername(username);
                System.out.println("Usuario cargado: " + userDetails.getUsername());
                if(jwtService.isTokenValid(token, userDetails)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    System.out.println("Autenticación exitosa.");
                }else{
                    System.out.println("Token inválido.");
                }
            } catch (Exception e) {
                System.out.println("Error al cargar usuario: " + e);
            }
        }
        filterChain.doFilter(request, response);
    }
        
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ", 0)){
            return authHeader.substring(7);
        }
        return null;
    }
}
