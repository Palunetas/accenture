package com.accenture.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    String greeting;

    @Bean
    public String ExecuteMethod(){
        System.out.println("El bean se ha creado");
        return "El bean ha sido creado";
    }

    //tenemos el metodo postCosntruct y verificamos en los logs que se ha inicializado
    @PostConstruct
    public void init() {
        System.out.println("El bean NotificationService ha sido creado e inicializado.");
        greeting = "Hola Desde el PostConstruct";
    }
}
