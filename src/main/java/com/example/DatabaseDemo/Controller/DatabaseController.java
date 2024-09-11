package com.example.DatabaseDemo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DatabaseDemo.Exceptions.ExceptionsWithMessageConflict;
import com.example.DatabaseDemo.Exceptions.ExceptionsWithMessageNotFound;
import com.example.DatabaseDemo.Repository.DatabaseRepository;
import com.example.DatabaseDemo.Service.DatabaseService;
import com.example.DatabaseDemo.model.User;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class DatabaseController {

    private final DatabaseRepository userRepository;
    private final DatabaseService databaseService;

    //!Alternative and simpler method without single name finder
    // @GetMapping("/get-all-users")
    // public ResponseEntity<List<User>> getAllUsers() {
    // List<User> users = userRepository.findAll();
    // return new ResponseEntity<>(users, HttpStatus.OK);
    // }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String userName) {
    List<User> users = databaseService.getUsers(userName);
    return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/get-user-by-id")
    public ResponseEntity<User> getUserById(@RequestParam Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //!Alternative and simpler method without custom exceptions and checks
    // @PostMapping("/create-user")
    // public ResponseEntity<User> createUser(@RequestBody User newUser) {
    //     System.out.println("Received User: " + newUser.toString());
    //     User createdUser = userRepository.save(newUser);
    //     return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    // }

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        System.out.println("Received User: " + newUser.toString()); //debugging
        User createdUser = databaseService.createUser(newUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    //!Alternative and simpler method without custom exceptions
    // @PutMapping("/update-user")
    // public ResponseEntity<User> updateUser(@RequestBody User updatedUser) {
    //     if (userRepository.existsById(updatedUser.getId())) {
    //         User savedUser = userRepository.save(updatedUser);
    //         return new ResponseEntity<>(savedUser, HttpStatus.OK);
    //     } else {
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    // }

    @PutMapping("/update-user")
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser) {
        databaseService.updateUser(updatedUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    //!Alternative and simpler method without custom exceptions
    // @DeleteMapping("/delete-user-by-id")
    // public ResponseEntity<Void> deleteUserById(@RequestParam Long userId) {
    //     if (userRepository.existsById(userId)) {
    //         userRepository.deleteById(userId);
    //         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //     } else {
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    // }

    @DeleteMapping("/delete-user-by-id")
    public ResponseEntity<Void> deleteUserById(@RequestParam Long userId) {
        databaseService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //#region Exception Handlers
    //Exception Handlers for both CONFLICT and NOT_FOUND statuses respectively
    @ExceptionHandler(ExceptionsWithMessageConflict.class)
    public ResponseEntity<String> handleExceptionsWithMessageConflict(ExceptionsWithMessageConflict exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExceptionsWithMessageNotFound.class)
    public ResponseEntity<String> handleExceptionsWithMessageNotFound(ExceptionsWithMessageNotFound exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    //#endregion
}