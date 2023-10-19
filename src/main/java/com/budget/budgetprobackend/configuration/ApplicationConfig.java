package com.budget.budgetprobackend.configuration;

import com.budget.budgetprobackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuración de la aplicación que incluye la configuración de CORS, 
 * gestión de autenticación. 
 * 
 * @author QuinSDev
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Configuración de CORS para permitir solicitudes desde el origin frontend
     * @return una instancia nueva de CorsFilter que utiliza la configuración 
     * definida en la UrlBasedCorsConfigurationSource.
     */
    @Bean
    public CorsFilter corsFilter() {
        
        CorsConfiguration corsConfig = new CorsConfiguration();
        
        // Configura el origin permitido (frontend)
        corsConfig.addAllowedOrigin("http://localhost:5173");
        
        // Métodos HTTP permitidos
        corsConfig.addAllowedMethod(HttpMethod.GET);
        corsConfig.addAllowedMethod(HttpMethod.POST);
        corsConfig.addAllowedMethod(HttpMethod.PUT);
        corsConfig.addAllowedMethod(HttpMethod.DELETE);
        
        // Permitir todas las cabeceras
        corsConfig.addAllowedHeader("*");
        
        /* Crea una instancia de UrlBasedCorsConfigurationSource, que se 
        utiliza para configurar CORS basado en URL */
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        
        /* Registra la configuración CORS(CorsConfiguration) para que aplique
        a todas la rutas ("/**"). */
        source.registerCorsConfiguration("/**", corsConfig);
        
        return new CorsFilter(source);
    }
    /**
     * Define un administrador de autenticación personalizado.
     * 
     * @param config: La configuración de autenticación inyectada, que permite
     * acceder al administrador de autenticación
     * @return El administrador de autenticación personalizado
     * @throws Exception: Si ocurren errores durante la configuración o
     * recuperación del administrador.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) 
            throws Exception {
        return config.getAuthenticationManager();
    }
    
    /**
     * Define un proveedor de autenticación personalizado.
     * Este método configura y proporcioa un proovedor de autenticación que
     * utiliza un servicio de detalles de usuario y un codificador de contraseñas.
     * 
     * @return El proveedor de autenticación personalizado configurado.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    
    /**
     * Define un servicio de detalles de usuario personalizado.
     * Este método configura y proporciona un servicio de detalles de usuario
     * que recupera información del usuario desde una base de datos.
     * 
     * @return El servicio de detalles de usuario personalizado configurado.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return userName -> userRepository.finByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    
    /**
     * Define un codificador de contraseñas personalizados.
     * Este método configuraq y proporciona un codificador de contraseñas 
     * seguro (BCryptPasswordEncoder).
     * 
     * @return El codificador de contraseñas personalizado configurado.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}