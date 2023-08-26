package com.example.booklendapp.LoanService;

import com.example.booklendapp.entity.Book;
import com.example.booklendapp.entity.Loan;
import com.example.booklendapp.entity.User;
import com.example.booklendapp.exception.BookIsNotAvailableException;
import com.example.booklendapp.exception.ResourceNotFoundException;
import com.example.booklendapp.repository.BookRepository;
import com.example.booklendapp.repository.LoanRepository;
import com.example.booklendapp.repository.UserRepository;
import com.example.booklendapp.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class BorrowBook {
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private LoanRepository loanRepository;
    @InjectMocks
    private LoanService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void borrowBook_withUserIdNotFound_throwsResourceNotFoundException() {
        //given
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                service.borrowBook(1, anyLong()));
        //then
        assertThat(exception).hasMessageContaining("User with given id not found !");
        verify(bookRepository, never()).findById(anyLong());
        verify(userRepository, never()).save(any());

    }

    @Test
    void borrowBook_withBookIdNotFound_throwsResourceNotFoundException() {
        //given
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        User user = new User();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        //when
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> service.borrowBook(user.getId(), anyLong()));
        //then
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class);
        assertThat(exception).hasMessageContaining("Book with given id not found !");
        verify(loanRepository, never()).save(any());
    }

    @Test
    void borrowBook_withNotAvailableBook_throwsBookIsNotAvailableException() {
        //given
        Book book = new Book();
        book.setAvailable(false);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mock(User.class)));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        //when
        Exception exception = assertThrows(BookIsNotAvailableException.class, () -> service.borrowBook(anyLong(), book.getId()));
        //then
        assertThat(exception).hasMessageContaining("Book with given id is not available !");
        verify(loanRepository, never()).save(any());
    }

    @Test
    void borrowBook_createLoan() {
        //given
        User user = new User();
        Book book = new Book();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));
        //when
        Loan loan = service.borrowBook(user.getId(), book.getId());

        //then
        assertFalse(book.isAvailable());
        assertNotNull(loan);
        assertEquals(user,loan.getUser());
        assertEquals(book,loan.getBook());
        assertEquals(LocalDate.now(),loan.getLoanDate());
        assertEquals(LocalDate.now().plusWeeks(2),loan.getDeadline());
    }
}