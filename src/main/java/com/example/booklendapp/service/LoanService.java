package com.example.booklendapp.service;

import com.example.booklendapp.entity.Book;
import com.example.booklendapp.entity.Loan;
import com.example.booklendapp.entity.User;
import com.example.booklendapp.exception.BookIsNotAvailableException;
import com.example.booklendapp.exception.LoanIsNotAvailable;
import com.example.booklendapp.exception.ResourceNotFoundException;
import com.example.booklendapp.model.LoanReadModel;
import com.example.booklendapp.repository.BookRepository;
import com.example.booklendapp.repository.LoanRepository;
import com.example.booklendapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }
    public Loan borrowBook(long userId, long bookId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given id not found !"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book with given id not found !"));

        if (book.isAvailable()){
            Loan loan = new Loan();
            loan.setUser(user);
            loan.setBook(book);
            loan.setLoanDate(LocalDate.now());
            loan.setDeadline(LocalDate.now().plusWeeks(2));
            book.setAvailable(false);
            List<Book> books = user.getBooks();
            books.add(book);
            return loanRepository.save(loan);
        }else {
            throw new BookIsNotAvailableException("Book with given id is not available !");
        }
    }
    public Loan returnBorrowBook(long id){
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan with given id not found !"));
        if (!loan.isLoanStatus()){
            throw new LoanIsNotAvailable("Loan is not available to change !");
        }
        User user = loan.getUser();
        Book book = loan.getBook();
        List<Book> books = user.getBooks();
        books.remove(book);
        book.setAvailable(true);
        loan.setLoanStatus(false);
        return loanRepository.save(loan);
    }

    public List<LoanReadModel> readAll() {
        return loanRepository.findAll().stream()
                .map(LoanReadModel::new)
                .collect(Collectors.toList());
    }
}
