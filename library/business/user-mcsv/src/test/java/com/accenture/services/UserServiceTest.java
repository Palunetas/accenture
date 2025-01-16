package com.accenture.services;

import com.accenture.entities.User;
import com.accenture.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class UserServiceTest {


    @Mock
    private UserRepository userRepository;  // Simulamos el repositorio

    @InjectMocks
    private UserService userService;  // El servicio será inyectado con el repositorio simulado

    private User user;
    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        user = new User("John Doe", "password", "john.doe@example.com", true);
        user1 = new User("John Doe", "password", "john.doe@example.com", true);
        user2 = new User("Jane Smith", "password123", "jane.smith@example.com", true);
    }

    @Test
    public void testFindById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Llamamos al servicio
        Optional<User> result = userService.findById(1L);

        // Verificamos que el resultado sea el esperado
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
    }

    @Test
    public void testFindByIdNotFound() {
        // Configura el comportamiento del mock para devolver Optional.empty(), simulando que no se encuentra el usuario
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // Llamamos al servicio
        Optional<User> result = userService.findById(999L);

        // Verificamos que el resultado esté vacío (usuario no encontrado)
        assertFalse(result.isPresent());
    }

    @Test
    public void testSave() {
        // Configura el comportamiento del mock
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Llamamos al servicio
        User result = userService.save(user);

        // Verificamos que el resultado sea el esperado
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    public void testSaveWithError() {
        // Simula que el repositorio lanza una excepción (por ejemplo, error de base de datos)
        when(userRepository.save(user)).thenThrow(new DataAccessException("Database error") {});

        // Verificamos que la excepción sea lanzada
        Exception exception = assertThrows(DataAccessException.class, () -> {
            userService.save(user);  // Llamada al servicio que debe lanzar la excepción
        });

        // Verificamos el mensaje de la excepción
        assertEquals("Database error", exception.getMessage());
    }

    @Test
    public void testFindAll() {
        // Configura el comportamiento del mock para devolver una lista de usuarios
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        // Llamamos al servicio
        Iterable<User> result = userService.findAll();

        // Verificamos que los resultados no son vacíos
        assertNotNull(result);
        assertTrue(result.iterator().hasNext());  // Verifica que el iterable no esté vacío

        // Verificamos que los usuarios en la lista son los esperados
        assertEquals(2, ((Collection<?>) result).size());  // Asegura que hay 2 usuarios
    }

    @Test
    public void testFindAllEmpty() {
        // Configura el comportamiento del mock para devolver una lista vacía
        when(userRepository.findAll()).thenReturn(List.of());

        // Llamamos al servicio
        Iterable<User> result = userService.findAll();

        // Verificamos que el resultado esté vacío
        assertNotNull(result);
        assertFalse(result.iterator().hasNext());  // Verifica que el iterable está vacío
    }
}
