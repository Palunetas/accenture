package com.accenture.services;

import com.accenture.entities.Book;
import com.accenture.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> isBookExist(String isbn){
        return bookRepository.findByIsbn(isbn);
    }

}
