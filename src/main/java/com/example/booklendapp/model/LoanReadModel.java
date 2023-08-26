package com.example.booklendapp.model;

import com.example.booklendapp.entity.Book;
import com.example.booklendapp.entity.Loan;
import com.example.booklendapp.entity.User;
import lombok.Data;

import java.time.LocalDate;

@Data

public class LoanReadModel {
    private long loanId;
    private boolean loanStatus;
    private String firstName;
    private String lastName;
    private Book book;
    private LocalDate loanDate;
    private LocalDate deadline;

    public LoanReadModel(Loan loan){
       this.book=loan.getBook();
       this.firstName=loan.getUser().getFirstName();
       this.lastName=loan.getUser().getLastName();
        this.loanDate=loan.getLoanDate();
        this.deadline=loan.getDeadline();
        this.loanId=loan.getId();
        this.loanStatus=loan.isLoanStatus();
    }
}
