package com.accenture.exception.handler;

import com.accenture.controllers.UserController;
import com.accenture.entities.User;
import com.accenture.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean  // Simula el servicio UserService para las pruebas
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;  // Para convertir objetos a JSON

    @Test
    public void testHandleBadRequestException() throws Exception {
        // Crear un usuario con nombre vacío y un correo inválido
        User invalidUser = new User(null, "123456", "invalid-email", true);  // 'name' es null y 'email' es inválido

        // Realiza la solicitud POST y verifica que se lanza la BadRequestException
        mockMvc.perform(post("/new-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidUser)))  // Convierte el usuario inválido a JSON
                .andExpect(status().isBadRequest())  // Verifica que el estado es 400 Bad Request
                .andExpect(jsonPath("$.errorCode").value("BAD_REQUEST"))  // Verifica que el código de error es BAD_REQUEST
                .andExpect(jsonPath("$.errorMessages").isArray())  // Verifica que 'errorMessages' es un array
                .andExpect(jsonPath("$.errorMessages", Matchers.hasItems(
                "Invalid email address", "Name cannot be empty"
        )));
    }


    @Test
    public void testHandleNotFoundException() throws Exception {
        // Simula que el usuario con ID 8 no existe
        mockMvc.perform(get("/8"))  // URL para un ID no existente
                .andExpect(status().isNotFound())  // Verifica que el estado es 404 Not Found
                .andExpect(jsonPath("$.errorCode").value("NOT_FOUND"))  // Verifica el código de error
                .andExpect(jsonPath("$.errorMessages[0]").value("User not found for ID: 8"));  // Verifica el mensaje de error
    }
}