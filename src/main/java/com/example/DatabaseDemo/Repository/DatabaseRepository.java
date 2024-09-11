package com.example.DatabaseDemo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.DatabaseDemo.model.User;

public interface DatabaseRepository extends JpaRepository<User, Long> {
    List<User> findAllByUserName(String userName);
    Optional<User> findByUserName(String userName);
}
