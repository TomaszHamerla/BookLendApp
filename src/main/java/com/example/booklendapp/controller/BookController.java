package com.example.booklendapp.controller;

import com.example.booklendapp.entity.Book;
import com.example.booklendapp.exception.InvalidBookDataException;
import com.example.booklendapp.exception.ResourceNotFoundException;
import com.example.booklendapp.model.BookDto;
import com.example.booklendapp.model.BookUpdateDto;
import com.example.booklendapp.service.BookService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService service;
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<Book> createBook(@Valid @RequestBody BookDto bookDto) {
        Book result = service.createBook(bookDto);
        logger.info("Book created !");
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping
    ResponseEntity<List<Book>> readAllBooks() {
        return ResponseEntity.ok(service.readAllBooks());
    }

    @GetMapping("/searchByAvailable")
    ResponseEntity<List<Book>> readByAvailable(@RequestParam(defaultValue = "true") boolean available) {
        return ResponseEntity.ok(service.readByAvailable(available));
    }

    @GetMapping("/{id}")
    ResponseEntity<Book> readBook(@PathVariable long id) {

        Book book = service.readBookById(id);
        return ResponseEntity.ok(book);

    }

    @GetMapping("/findByPrefix")
    ResponseEntity<List<Book>> readBooksByPrefix(@RequestParam(name = "authorPrefix", required = false) String authorPrefix, @RequestParam(name = "titlePrefix", required = false) String titlePrefix) {
        List<Book> books = service.readBooksByPrefix(authorPrefix, titlePrefix);
        if (books.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(books);
        }
    }

    @PatchMapping("/{id}")
    ResponseEntity<Book> bookUpdate(@RequestBody BookUpdateDto bookUpdateDto, @PathVariable long id) {

        Book book = service.updateBook(bookUpdateDto, id);
        logger.info("Book updated !");
        return ResponseEntity.ok(book);

    }

    @DeleteMapping("/{id}")
    ResponseEntity<Book> deleteBook(@PathVariable long id) {

        service.deleteBook(id);
        return ResponseEntity.ok().build();

    }
}

