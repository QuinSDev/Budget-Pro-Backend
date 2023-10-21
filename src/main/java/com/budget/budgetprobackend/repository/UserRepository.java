package com.budget.budgetprobackend.repository;

import com.budget.budgetprobackend.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Este repositorio se utiliza para acceder a los datos relacionados con la
 * entidad 'User' en la base de datos.
 * Proporciona métodos par realizar operaciones de acceso a datos relacionados
 * con los usuarios en la base de datos.
 * 
 * @author QuinSDev
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>{
    
    /**
     * Busca un usuario por su nombre de usuario.
     * 
     * @param userName El nombre de usuario del usuario que se desea buscar.
     * @return Un objeto Optional que puede contener el usuario si se
     * encuentra, o estar vacío si no se encuentra.
     */
    Optional<User> findByUserName(String userName);
    
}