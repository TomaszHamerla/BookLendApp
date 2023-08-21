package com.example.booklendapp.model;

import lombok.Data;


@Data
public class UserUpdateDto {
    private String firstName;

    private String lastName;

    private  String email;
}
