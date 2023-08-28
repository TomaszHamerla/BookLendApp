package com.example.booklendapp.controller;

import com.example.booklendapp.entity.Loan;
import com.example.booklendapp.exception.BookIsNotAvailableException;
import com.example.booklendapp.exception.LoanIsNotAvailable;
import com.example.booklendapp.exception.ResourceNotFoundException;
import com.example.booklendapp.model.LoanReadModel;
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
    ResponseEntity<List<LoanReadModel>>readAllLoans(){
        return ResponseEntity.ok(service.readAll());
    }
    @GetMapping("/searchByLoanStatus")
        ResponseEntity<List<LoanReadModel>>readByLoanStatus(@RequestParam(defaultValue = "true")boolean status){
            return ResponseEntity.ok(service.readByLoanStatus(status));
        }

    @PostMapping("/{userId}/{bookId}")
    ResponseEntity<LoanReadModel>borrowBook(@PathVariable long userId, @PathVariable long bookId){

            Loan loan = service.borrowBook(userId, bookId);
            LoanReadModel loanReadModel = new LoanReadModel(loan);
            return ResponseEntity.created(URI.create("/"+loan.getId())).body(loanReadModel);


    }

    @PatchMapping("/{id}")
    ResponseEntity<LoanReadModel>returnBorrowBook(@PathVariable long id){

            Loan loan = service.returnBorrowBook(id);
            return ResponseEntity.ok(new LoanReadModel(loan));

    }
}
