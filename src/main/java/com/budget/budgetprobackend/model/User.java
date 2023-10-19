package com.budget.budgetprobackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Esta clase representa un usuario en la aplicación. Esta clase se mapea a la 
 * tabla "user" en la base de datos.
 * Implementa la interfaz UserDetails de Spring Security para gestionar la 
 * autenticación y autorización.
 * 
 * @author QuinSDev
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user", uniqueConstraints = {@UniqueConstraint(columnNames = 
        {"userName"})})
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @Column(nullable = false)
    String userName;
    String firstName;
    String lastName;
    String password;
    @Enumerated(EnumType.STRING)
    Role role;
    
    /**
     * Obtiene las autoridades (roles) asignadas al usuario.
     * @return Una lista de autoridades, donde cada autoridad es un rol que 
     * posee el usuario.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
    }
    
    /**
     * Obtiene el nombre de usuario del usuario.
     * @return El nombre de usuario del usuario.
     */
    @Override
    public String getUsername() {
        return this.userName;
    }
    
    /**
     * Comprueba si la cuenta del usuario no ha expirado.
     * @return true si la cuenta del usuario no ha expirado.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    /**
     * Comprueba si la cuenta del usuario no está bloqueada.
     * @return true si la cuenta del usuario no está bloqueada.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    /**
     * Comprueba si las credenciales del usuario no han expirado.
     * @return true si las credenciales del usuario no han expirado.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    /**
     * Comprueba si el usuario está habilitado.
     * @return true si el usuario está habilitado.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
