package com.example.booklendapp.entity;

import com.example.booklendapp.model.UserDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private  String email;

    public User (UserDto userDto){
        this.firstName= userDto.getFirstName();
        this.lastName= userDto.getLastName();
        this.email= userDto.getEmail();
    }

}
