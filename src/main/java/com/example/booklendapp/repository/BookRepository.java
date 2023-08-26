package com.example.booklendapp.repository;

import com.example.booklendapp.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book>findAllByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCase(String author, String title);
    List<Book>findAllByAvailable(boolean available);
}
