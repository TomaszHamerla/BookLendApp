package com.example.booklendapp.repository;

import com.example.booklendapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    List<User>findAllByFirstNameContainsIgnoreCase(String name);
}
