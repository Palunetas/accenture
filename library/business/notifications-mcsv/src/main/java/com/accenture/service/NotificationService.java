package com.accenture.service;

import org.springframework.stereotype.Component;

@Component
public class NotificationService {
        // Constructor del servicio
        public NotificationService() {
            System.out.println("NotificationService creado");
        }

        // Método para enviar notificaciones
        public String sendNotification(String message) {
            System.out.println("Enviando notificación: " + message);
            return message;
        }

}
