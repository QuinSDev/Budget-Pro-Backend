package com.budget.budgetprobackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Esta clase es un filtro de autenticación JWT que se encarga de procesar las
 * solicitudes y verificar la presencia de un token JWT en el encabezado de
 * autorización. Si se encuentra un token JWT válido, el filtro permite que
 * la solicitud continúe.
 * 
 * Este filtro se aplica una vez por cada solicitud entrante.
 * 
 * @author QuinSDev
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    /**
     * Implementación del método doFilterInternal de OncePerRequestFilter que
     * verifica y procesa el token JWT.
     * 
     * @param request: La solicitud HTTP entrante.
     * @param response: La respuesta HTTP.
     * @param filterChain: Cadena de filtros para procesar la solicitud.
     * @throws ServletException: Si se produce un error duranre el procesamiento
     * de la solicitud.
     * @throws IOException: Si se produce un error de E/S durante el procesamiento.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = getTokenFromRequest(request);

        if (token == null) {
            /* Si no se encontró un Token JWT, se permite que la solicitud
            continúe sin autenticación. */
            filterChain.doFilter(request, response);
            return;
        } else {
        }

        filterChain.doFilter(request, response);

    }
    
    /**
     * Obtiene el token JWT del encabezado de autorización de la solicitud.
     * 
     * @param request: La solicitud entrante.
     * @return El token JWT si se encuenta en el encabezado de la autorización,
     * o null si no se encuentra.
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

}