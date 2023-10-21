package com.budget.budgetprobackend.service;

import com.budget.budgetprobackend.exception.MyException;
import com.budget.budgetprobackend.model.Role;
import com.budget.budgetprobackend.model.User;
import com.budget.budgetprobackend.repository.UserRepository;
import com.budget.budgetprobackend.request.RegisterRequest;
import com.budget.budgetprobackend.response.AuthResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio de autenticación y registro de usuarios.
 * Esta clase contiene métodos para registrar nuevos usuarios en el sistema,
 * para loguearse y realizar la validación de los datos de registro. También
 * de tokens de autenticación.
 * 
 * @author QuinSDev
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * Registraun nuevo usuario en el sistema.
     * 
     * @param request: La solicitud de registro que contiene los datos del nuevo
     *                 usuario.
     * @return Una respuesta de autenticación que incluye un token si el 
     *         registro es exitoso, o un mensaje de error en caso contrario.
     */
    @Transactional
    public AuthResponse registerUser(RegisterRequest request) {
        
        try {
            validUser(request);
            
            // Crea un objeto User con los datos proporcionados por el usuario.
            User user = User.builder()
                        .userName(request.getUserName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .role(Role.USER)
                        .build();
            
            // Guarda el usuario en la base de datos.
            userRepository.save(user);
            
            // Devuelve el token de auntenticación para el usuario registrado.
            return AuthResponse.builder()
                    .token(jwtService.getToken(user))
                    .build();
                    
        } catch (MyException ex) {
            return new AuthResponse("Error en el registro: " + ex.getMessage());
        }

    }
    
    /**
     * Realiza la validación de los datos de registro.
     * 
     * @param request: La solicitud de registro que se va a validar.
     * @throws MyException: Si se encuentran problemas en los datos de registro,
     *                      se lanza una excepción con un mensaje descriptivo 
     *                      del problema.
     */
    @Transactional
    public void validUser(RegisterRequest request) throws MyException {
        
        if (request.getUserName() == null || request.getUserName().isEmpty()) {
            throw new MyException("El nombre de usuario no puede estar vacío");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new MyException("La contraseña no puede estar vacía");
        }
        if (request.getFirstName() == null || request.getFirstName().isEmpty()) {
            throw new MyException("El nombre no puede estar vacío");
        }
        if (request.getLastName() == null || request.getLastName().isEmpty()) {
            throw new MyException("El apellido no puede estar vacío");
        }
        
        if (request.getPassword().length() <= 5) {
            throw new MyException("La contraseña no debe tener menos de 5 caracteres");
        }
        
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new MyException("Las contraseñas no coinciden");
        }

    }
    
}