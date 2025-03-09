package com.accenture.controllers;

import com.accenture.entities.Book;
import com.accenture.exception.BadRequestException;
import com.accenture.services.BookService;
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
public class BookController {

    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @PostMapping("/add-book")
    public ResponseEntity<Book> saveBook(@Valid @RequestBody Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            // Creamos una lista para almacenar los errores
            List<String> errorMessages = new ArrayList<>();

            // Añadimos los mensajes de error a la lista
            bindingResult.getAllErrors().forEach(error -> {
                logger.error("Libro no guardado " + book.toString());
                errorMessages.add(error.getDefaultMessage());
            });

            // Lanzamos la excepción con la lista de errores como un solo string
            throw new BadRequestException(errorMessages);  // Aquí pasamos los errores como una lista de String
        }
        Book bookResponse = bookService.save(book);
        logger.info("Libro guardado "+book.toString());
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }

    //metodo para regresar libro si existe, se conectara con el microsercivio Loans
    @GetMapping("/{isbn}")
    public Boolean isBookInInventory(@PathVariable String isbn){
        Optional<Book> book = bookService.isBookExist(isbn);
        return book.isPresent();
    }
}
