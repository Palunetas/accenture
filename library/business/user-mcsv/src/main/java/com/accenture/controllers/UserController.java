package com.accenture.controllers;

import com.accenture.entities.User;
import com.accenture.exception.BadRequestException;
import com.accenture.exception.NotFoundException;
import com.accenture.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/new-user")
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            // Creamos una lista para almacenar los errores
            List<String> errorMessages = new ArrayList<>();

            // Añadimos los mensajes de error a la lista
            bindingResult.getAllErrors().forEach(error -> {
                logger.error("Usuario no guardado " + user.toString());
                errorMessages.add(error.getDefaultMessage());
            });

            // Lanzamos la excepción con la lista de errores como un solo string
            throw new BadRequestException(errorMessages);  // Aquí pasamos los errores como una lista de String
        }
        User userResponse = userService.save(user);
        logger.info("Usuario guardado "+user.toString());
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public Iterable<User> getAll(){

        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@Valid @PathVariable Long id) {
       logger.info("Buscando usuario con ID: " + id);

        // Buscar el usuario por ID
        Optional<User> user = userService.findById(id);

        // Si no se encuentra el usuario, devolvemos un error 404
        if (user.isEmpty()) {
            logger.info("Usuario no encontrado "+id);
            throw new NotFoundException("User not found for ID: " + String.valueOf(id));
        }

        // Si se encuentra el usuario, lo devolvemos con código 200
        logger.info("Usuario encontrado con id "+id);
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }
}
