package com.example.DatabaseDemo.Service;

import java.util.List;
import java.util.Optional;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.DatabaseDemo.Exceptions.ExceptionsWithMessageConflict;
import com.example.DatabaseDemo.Exceptions.ExceptionsWithMessageNotFound;
import com.example.DatabaseDemo.Repository.DatabaseRepository;
import com.example.DatabaseDemo.model.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DatabaseService {
    public final DatabaseRepository userRepository;

    //Search with a name & without a username - If a username is provided, return only that user
    public List<User> getUsers(String userName){
        if (userName == null){
            return userRepository.findAll();
        } else {
            return userRepository.findAllByUserName(userName);
        }
    }

    //Check if a user already exists with the chosen username before creating a user
    public User createUser(User newUser){
        Optional<User> UserByName = userRepository.findByUserName(newUser.getUserName());
        if(UserByName.isPresent()){
            throw new ExceptionsWithMessageConflict("User already exists with username: " + newUser.getUserName());
        }
        return userRepository.save(newUser);
    }

    //Delete the user with given id if it exists or throw a not found status
    public void deleteUser(Long userId){
        if(userRepository.existsById(userId)){
            userRepository.deleteById(userId);
        } else {
            throw new ExceptionsWithMessageNotFound("User with the id \"" + userId + "\" does not exist.");
        }
    }

    //Update user with given values (based on user's id)
    public void updateUser(User updatedUser){
        //User oldUser = userRepository.findById(userId)
        //                                .orElseThrow(() -> new ExceptionsWithMessage("User not found with id: " + userId));
        if (userRepository.existsById(updatedUser.getId())){
            userRepository.save(updatedUser);
        } else {
            throw new ExceptionsWithMessageNotFound("User with the id \"" + updatedUser.getId() + "\" does not exist.");
        }
    }
}
