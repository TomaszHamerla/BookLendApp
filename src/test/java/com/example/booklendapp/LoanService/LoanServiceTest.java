package com.example.booklendapp.LoanService;

import com.example.booklendapp.entity.User;
import com.example.booklendapp.exception.ResourceNotFoundException;
import com.example.booklendapp.repository.BookRepository;
import com.example.booklendapp.repository.LoanRepository;
import com.example.booklendapp.repository.UserRepository;
import com.example.booklendapp.service.LoanService;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class LoanServiceTest {
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
    void borrowBook_withBookIdNotFound_throwsResourceNotFoundException(){
        //given
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        User user = new User();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        //when
        Exception exception=assertThrows(ResourceNotFoundException.class,() -> service.borrowBook(user.getId(),anyLong()));
        //then
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class);
        assertThat(exception).hasMessageContaining("Book with given id not found !");
        verify(loanRepository,never()).save(any());
    }
    @Test
    void borrowBook_withNotAvailableBook_throwsBookIsNotAvailableException(){
        //given

    }
}