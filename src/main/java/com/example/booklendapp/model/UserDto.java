package com.example.booklendapp.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
    private String firstName;
    @NotBlank(message = "Nazwisko nie moze byc puste !")
    private String lastName;
    @Email(message = "Bledny email !")
    @NotBlank(message = "Email nie moze byc pust !")
    private  String email;
}
