package com.example.booklendapp.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookDto {
    @NotBlank(message = "Tytul nie moze byc pusty !")
    private String title;
    @NotBlank(message = "Autor nie moze byc pusty !")
    private String author;
}
