package com.example.booklendapp.controller;

import com.example.booklendapp.entity.Loan;
import com.example.booklendapp.exception.BookIsNotAvailableException;
import com.example.booklendapp.exception.ResourceNotFoundException;
import com.example.booklendapp.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private final LoanService service;
    private final Logger logger = LoggerFactory.getLogger(LoanController.class);

    public LoanController(LoanService service) {
        this.service = service;
    }
    @GetMapping
    ResponseEntity<List<Loan>>readAllLoans(){
        return ResponseEntity.ok(service.readAll());
    }
    @PostMapping("/{userId}/{bookId}")
    ResponseEntity<Loan>borrowBook(@PathVariable long userId,@PathVariable long bookId){
        try{
            Loan loan = service.borrowBook(userId, bookId);
            return ResponseEntity.created(URI.create("/"+loan.getId())).body(loan);
        }catch (ResourceNotFoundException e){
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }catch (BookIsNotAvailableException e){
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}")
    ResponseEntity<Loan>returnBorrowBook(@PathVariable long id){
        try{
            Loan loan = service.returnBorrowBook(id);
            return ResponseEntity.ok(loan);
        }catch (ResourceNotFoundException e){
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}