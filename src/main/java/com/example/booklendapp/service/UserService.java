package com.example.booklendapp.service;

import com.example.booklendapp.entity.User;
import com.example.booklendapp.exception.ResourceNotFoundException;
import com.example.booklendapp.model.UserDto;
import com.example.booklendapp.model.UserUpdateDto;
import com.example.booklendapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User createUser(UserDto userDto) {
        return repository.save(new User(userDto));
    }

    public List<User> readAllUsers() {
        return repository.findAll();
    }

    public List<User>readUsersByName(String name){
        return repository.findAllByFirstNameContainsIgnoreCase(name);
    }

    public User readUser(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with given id not found !"));
    }

    public void deleteUser(long id) {
        existsById(id);

        repository.deleteById(id);
    }

    public User updateUser(UserUpdateDto userUpdateDto, long id) {
        existsById(id);

        User user = repository.findById(id).get();
        if (userUpdateDto.getFirstName() != null ) {
            user.setFirstName(userUpdateDto.getFirstName());
        }
        if (userUpdateDto.getLastName() != null) {
            user.setLastName(userUpdateDto.getLastName());
        }
        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }

        return repository.save(user);


    }

    private void existsById(long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("User with given id not found !");

        }

    }
}