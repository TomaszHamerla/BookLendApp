package com.example.booklendapp.LoanService;

import com.example.booklendapp.entity.Book;
import com.example.booklendapp.entity.Loan;
import com.example.booklendapp.entity.User;
import com.example.booklendapp.exception.ResourceNotFoundException;
import com.example.booklendapp.repository.LoanRepository;
import com.example.booklendapp.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ReturnBorrowBook {
    @Mock
    private LoanRepository loanRepository;
    @InjectMocks
    private LoanService service;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void returnBorrowBook_withLoanIdNotFound_throwsResourceNotFoundException(){
        //given
        when(loanRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        Exception exception = assertThrows(ResourceNotFoundException.class,() -> service.returnBorrowBook(anyLong()));
        //then
        assertThat(exception).hasMessageContaining("Loan with given id not found !");
        verify(loanRepository,never()).save(any());
    }
    @Test
    void returnBorrowBook_withLoanIdFound(){
        //given
        User user = new User();
        Book book = new Book();
        Loan loan = new Loan();
        List<Book>books =new ArrayList<>();
        book.setAvailable(false);
        books.add(book);
        user.setBooks(books);
        loan.setUser(user);
        loan.setBook(book);

        when(loanRepository.findById(anyLong())).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //when
        Loan returnLoanBook = service.returnBorrowBook(loan.getId());
        //then
        assertNotNull(returnLoanBook);
        assertTrue(book.isAvailable());
        assertTrue(user.getBooks().isEmpty());

    }
}
