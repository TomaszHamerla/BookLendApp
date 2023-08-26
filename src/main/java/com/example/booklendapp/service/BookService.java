package com.example.booklendapp.service;

import com.example.booklendapp.entity.Book;
import com.example.booklendapp.exception.InvalidBookDataException;
import com.example.booklendapp.exception.ResourceNotFoundException;
import com.example.booklendapp.model.BookDto;
import com.example.booklendapp.model.BookUpdateDto;
import com.example.booklendapp.repository.BookRepository;
import com.example.booklendapp.service.operations.CheckDateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements CheckDateService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Book createBook(BookDto bookDto) {
        return repository.save(new Book(bookDto));
    }

    public Book readBookById(long id) {
        existsById(id);
        return repository.findById(id).get();
    }

    public List<Book> readAllBooks() {
        return repository.findAll();
    }
    public List<Book>readByAvailable(boolean available){
        return repository.findAllByAvailable(available);
    }

    public List<Book> readBooksByPrefix(String author, String title) {
        return repository.findAllByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCase(author, title);
    }

    public Book updateBook(BookUpdateDto bookUpdateDto, long id) {
        existsById(id);

        Book book = repository.findById(id).get();
        if (bookUpdateDto.getAuthor() != null) {
            checkData(bookUpdateDto.getAuthor());
            book.setAuthor(bookUpdateDto.getAuthor());
        }
        if (bookUpdateDto.getTitle() != null) {
            checkData(bookUpdateDto.getTitle());
            book.setTitle(bookUpdateDto.getTitle());
        }
        return repository.save(book);
    }

    @Override
    public void checkData(String date) {
        if (date.isBlank()) {
            throw new InvalidBookDataException("Title and author can not be blank !");
        }
    }

    public void deleteBook(long id) {
        existsById(id);
        repository.deleteById(id);
    }

    private void existsById(long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Book with given id not found !");
        }
    }
}
