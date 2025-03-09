package com.accenture.controller;

import com.accenture.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("notify")
    public String message(){
        return notificationService.sendNotification("El usuario no ha regresado el libro #9988");
    }
}
