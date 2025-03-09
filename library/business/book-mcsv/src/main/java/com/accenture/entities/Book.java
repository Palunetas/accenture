package com.accenture.entities;

import com.accenture.configuration.annotation.MinPages;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "libros")
public class Book {

    //Constructor vacio necesario para la creacion y el retorno de la entidad
    public Book() {
    }

    public Book(String isbn, String title, String description, Integer numPages) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.numPages = numPages;
    }

    // id y generate value para marcar el Id a la referencia de la tabla y
    //el generation type para crear id en automatico
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //anotacion para la validacion y para marcar como campo unico
    //no podra haber dos registros con el mismo ISBN en la db
    @NotEmpty(message = "ISBN cannot be empty")
    @Column(unique = true)
    private String isbn;

    @NotEmpty(message = "Title cannot be empty")
    private String title;
    private String description;

    //anotacion personalizada para el numero de paginas
    @MinPages(message = "The number o pages must be at least 100")
    @Column(name = "numpages")
    private Integer numPages ;

    //Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumPages() {
        return numPages;
    }

    public void setNumPages(Integer numPages) {
        this.numPages = numPages;
    }

    //toString para cuando se requiera devolver el objeto en string
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", numPages=" + numPages +
                '}';
    }
}
