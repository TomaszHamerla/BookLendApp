package com.example.booklendapp.service;

import com.example.booklendapp.entity.User;
import com.example.booklendapp.exception.ResourceNotFoundException;
import com.example.booklendapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository repository;
    @InjectMocks
    private UserService service;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void readUserById() {
        //given
        User user = new User();
        when(repository.findById(anyLong())).thenReturn(Optional.of(user));

        //when
        User userFound = service.readUserById(anyLong());

        //then
        assertNotNull(userFound);
        assertEquals(user,userFound);
    }
    @Test
    void readUserByIdNotFound(){
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        Exception exception=assertThrows(ResourceNotFoundException.class,()->service.readUserById(anyLong()));
        //then
        assertThrows(ResourceNotFoundException.class,()->service.readUserById(anyLong()));
        assertThat(exception).hasMessageContaining("User with given id not found !");


    }
}