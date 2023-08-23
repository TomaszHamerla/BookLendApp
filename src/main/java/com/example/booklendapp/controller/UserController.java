package com.example.booklendapp.controller;

import com.example.booklendapp.entity.User;
import com.example.booklendapp.exception.InvalidUserDataException;
import com.example.booklendapp.exception.ResourceNotFoundException;
import com.example.booklendapp.model.UserDto;
import com.example.booklendapp.model.UserUpdateDto;
import com.example.booklendapp.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    private final Logger logger= LoggerFactory.getLogger(UserController.class);

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<User>createUser(@Valid @RequestBody UserDto userDto){
        User result = service.createUser(userDto);
        logger.info("User created !");
        return ResponseEntity.created(URI.create("/"+result.getId())).body(result);
    }
    @GetMapping
    ResponseEntity<List<User>>readAllUsers(){
        logger.warn("Exposing all users !");
        return ResponseEntity.ok(service.readAllUsers());
    }
    @GetMapping("/{id}")
    ResponseEntity<User> readUser(@PathVariable long id) {
        try {
            User user = service.readUserById(id);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/findByFirstName/{name}")
    ResponseEntity<List<User>>readByName(@PathVariable String name){
        List<User> users = service.readUsersByName(name);
        return checkListOfUsers(users);
    }
    @GetMapping("/findByLastName/{lastName}")
    ResponseEntity<List<User>>readByLastName(@PathVariable String lastName){
        List<User> users = service.readUsersByLastName(lastName);
       return checkListOfUsers(users);
    }

    private ResponseEntity<List<User>> checkListOfUsers(List<User>users){

        if (users.isEmpty()){
            return ResponseEntity.noContent().build();
        }else
            return ResponseEntity.ok(users);
    }
    @PatchMapping("/{id}")
    ResponseEntity<User>updateUser(@RequestBody UserUpdateDto userUpdateDto, @PathVariable long id){
        try{
            User result = service.updateUser(userUpdateDto, id);
            logger.info("User updated !");
            return ResponseEntity.ok(result);
        }catch (ResourceNotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }catch (InvalidUserDataException e){
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{id}")
    ResponseEntity<User>deleteUser(@PathVariable long id){
        try{
            service.deleteUser(id);
            return ResponseEntity.ok().build();
        }catch (ResourceNotFoundException e){
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
