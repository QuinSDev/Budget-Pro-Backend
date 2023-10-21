package com.budget.budgetprobackend.exception;

/**
 * Esta es una clase de excepción personalizada utilizada en la aplicación.
 * Esta excepción se utiliza para representar situaciones excepcionales o errores
 * específicos en el funcionamiento de la aplicación. Se puede lanzar con un
 * mensaje descriptivo para proporcionar información sobre el error.
 * 
 * @author QuinSDev
 */
public class MyException extends Exception{
    
    /**
     * Crea una nueva instancia de MyException con un mensaje descriptivo.
     * 
     * @param msg: El mensaje que describe la causa de la excepción.
     */
    public MyException(String msg) {
        super(msg);
    }
    
}