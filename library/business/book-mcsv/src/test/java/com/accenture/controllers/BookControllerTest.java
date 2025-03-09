package com.accenture.controllers;

import com.accenture.entities.Book;
import com.accenture.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Optional;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)  // Carga solo el controlador para las pruebas
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private BookController bookController;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos a JSON

    private Book user;

    @BeforeEach
    public void setUp() {
        user = new Book("2232","100 anios de soledad" ,"drama",102);
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void testSaveBook() throws Exception {
        // Simula el comportamiento del servicio
        when(bookService.save(any(Book.class))).thenReturn(user);

        // Realiza una solicitud POST
        mockMvc.perform(post("/add-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isbn").value("2232"))
                .andExpect(jsonPath("$.description").value("drama"));

        // Verifica que el método save del servicio se llamó
        verify(bookService).save(any(Book.class));
    }



    @Test
    void testIsBookInInventory_BookExists_ReturnsTrue() throws Exception {
        // Arrange
        String isbn = "234567";
        Book book = new Book(isbn, "'1984'", "Distopía política en un régimen totalitario",328);
        when(bookService.isBookExist(isbn)).thenReturn(Optional.of(book));

        // Act
        MvcResult result = mockMvc.perform(get("/" + isbn))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String responseBody = result.getResponse().getContentAsString();
        assert responseBody.equals("true");
    }

    @Test
    void testIsBookInInventory_BookDoesNotExist_ReturnsFalse() throws Exception {
        // Arrange
        String isbn = "1234567890";
        when(bookService.isBookExist(isbn)).thenReturn(Optional.empty());

        // Act
        MvcResult result = mockMvc.perform(get("/" + isbn))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String responseBody = result.getResponse().getContentAsString();
        assert responseBody.equals("false");
    }
}
