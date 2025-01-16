package com.accenture.controllers;

import com.accenture.entities.User;
import com.accenture.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)  // Carga solo el controlador para las pruebas
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos a JSON

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("John Doe","123456" ,"john.doe@example.com",true);
    }

    @Test
    public void testSaveUser() throws Exception {
        // Simula el comportamiento del servicio
        when(userService.save(any(User.class))).thenReturn(user);

        // Realiza una solicitud POST
        mockMvc.perform(post("/new-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))  // Convierte el usuario a JSON
                .andExpect(status().isCreated())  // Verifica que el estado es 201 Created
                .andExpect(jsonPath("$.name").value("John Doe"))  // Verifica el valor de "name"
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));  // Verifica el valor de "email"

        // Verifica que el método save del servicio se llamó
        verify(userService).save(any(User.class));
    }

    @Test
    public void testSaveUserWithValidationError() throws Exception {
        // Simula un objeto User con un error de validación
        User invalidUser = new User(null, "123456", "invalid-email", true); // 'name' es null y 'email' es inválido

        // Realiza la solicitud POST y verifica que se lanza la BadRequestException
        mockMvc.perform(post("/new-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidUser)))  // Convierte el usuario inválido a JSON
                .andExpect(status().isBadRequest())  // Verifica que el estado es 400 Bad Request
                .andExpect(jsonPath("$.errorCode").value("BAD_REQUEST"))  // Verifica que el código de error es BAD_REQUEST
                .andExpect(jsonPath("$.errorMessages").isArray())  // Verifica que 'errorMessages' es un array
                .andExpect(jsonPath("$.errorMessages", hasItems(
                        "Invalid email address", "Name cannot be empty"
                )));

    }

    @Test
    public void testGetAllUsers() throws Exception {
        // Simula el comportamiento del servicio
        when(userService.findAll()).thenReturn(List.of(user));

        // Realiza una solicitud GET
        mockMvc.perform(get("/"))  // GET /users
                .andExpect(status().isOk())  // Verifica que el estado es 200 OK
                .andExpect(jsonPath("$[0].name").value("John Doe"))  // Verifica el valor de "name" en la lista
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));  // Verifica el valor de "email" en la lista
    }

    @Test
    public void testGetUserById() throws Exception {
        // Simula el comportamiento del servicio
        when(userService.findById(1L)).thenReturn(Optional.of(user));

        // Realiza una solicitud GET con un ID válido
        mockMvc.perform(get("/1")) // GET /users/{id}
                .andExpect(status().isOk())  // Verifica que el estado es 200 OK
                .andExpect(jsonPath("$.name").value("John Doe"))  // Verifica el valor de "name"
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));  // Verifica el valor de "email"
    }

    @Test
    public void testGetUserByIdNotFound() throws Exception {
        // Simula el comportamiento del servicio cuando el usuario no se encuentra
        when(userService.findById(1L)).thenReturn(Optional.empty());

        // Realiza una solicitud GET con un ID que no existe
        mockMvc.perform(get("/1"))  // GET /users/{id}
                .andExpect(status().isNotFound())  // Verifica que el estado es 404 Not Found
                .andExpect(jsonPath("$.errorMessages").value("User not found for ID: 1"));  // Verifica el mensaje de error
    }
}
