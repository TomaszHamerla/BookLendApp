package com.example.booklendapp.entity;

import com.example.booklendapp.model.BookDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String author;
    public Book(BookDto bookDto){
        this.title= bookDto.getTitle();
        this.author= bookDto.getAuthor();
    }
}
