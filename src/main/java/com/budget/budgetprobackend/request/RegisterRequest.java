package com.budget.budgetprobackend.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa una solicitud de registro. Contiene los datos 
 * necesarios para crear una cuenta de usuario, incluyendo nombre de usuario,
 * contraseña, confirmación de contraseña, nombre y apellido.
 * 
 * @author QuinSDev
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    
    String userName;
    String password;
    String confirmPassword;
    String firstName;
    String lastName;
}
