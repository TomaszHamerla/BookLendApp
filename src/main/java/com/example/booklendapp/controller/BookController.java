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
    private final Logger logger = LoggerFactory.getLogger(BookController.class);
    public BookController(BookService service) {
        this.service = service;
    }
    @PostMapping
    ResponseEntity<Book>createBook(@Valid @RequestBody BookDto bookDto){
        Book result = service.createBook(bookDto);
        logger.info("Book created !");
        return ResponseEntity.created(URI.create("/"+result.getId())).body(result);
    }
    @GetMapping
    ResponseEntity<List<Book>>readAllBooks(){
        return ResponseEntity.ok(service.readAllBooks());
    }
    @GetMapping("/{id}")
    ResponseEntity<Book>readBook(@PathVariable long id){
        try {
            Book book = service.readBookById(id);
            return ResponseEntity.ok(book);
        }catch (ResourceNotFoundException e){
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/findByPrefix/{prefix}")
    ResponseEntity<List<Book>>readBooksByPrefix(@PathVariable String prefix){
        List<Book> books = service.readBooksByPrefix(prefix,prefix);
        if (books.isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(books);
        }
    }
    @PatchMapping("/{id}")
    ResponseEntity<Book>bookUpdate(@RequestBody BookUpdateDto bookUpdateDto , @PathVariable long id){
        try{
            Book book = service.updateBook(bookUpdateDto, id);
            logger.info("Book updated !");
            return ResponseEntity.ok(book);
        }catch (ResourceNotFoundException e){
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }catch (InvalidBookDataException e){
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{id}")
    ResponseEntity<Book>deleteBook(@PathVariable long id){
        try{
            service.deleteBook(id);
            return ResponseEntity.ok().build();
        }catch (ResourceNotFoundException e){
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
