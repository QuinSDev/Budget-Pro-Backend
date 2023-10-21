package com.budget.budgetprobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Clase principal de la aplicación Spring Boot
 * Esta clase inicia la aplicación Spring Boot y configura la ubicación base para
 * escanear componentes y controladores de la aplicación.
 * 
 * @SpringBootApplication indica que esta clase es la clase principal
 *                        de la aplicación Spring Boot y configura automáticamente 
 *                        la aplicación.
 * @ComponentScan específica que la ubicación base para escaner componentes de
 *                la aplicación, asegurando que Spring encuentre y configure 
 *                todos los componentes y controladores necesarios.
 * 
 * @author QuinSDev
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.budget.budgetprobackend")
public class BudgetProBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetProBackendApplication.class, args);
	}

}