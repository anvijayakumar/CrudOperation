package com.example.crud.operation.service.serviceimpl;

import com.example.crud.operation.entity.User;
import com.example.crud.operation.exception.UserNotFoundException;
import com.example.crud.operation.repository.UserRepository;
import com.example.crud.operation.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<Object> getUserById(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("Id:" + id + " user details does not exist in the database."));
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("Message", "Records with the Id: " + id + " are shown");
            responseBody.put("Details",user);
            return new ResponseEntity<>(responseBody,HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Failed to fetch leave type: " + e.getMessage());
        }
    }


    @Override
    public ResponseEntity<Object> updateUser(Long id, User userDetails) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("Id " + id + " does not exist in the database."));
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            userRepository.save(user);
            String message = "User record of Id : " + id + " is updated successfully";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Failed to update leave type: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteUser(Long id) {
        try{
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("Id "+id+" does not exist in the database."));
            userRepository.deleteById(user.getId());
            String message = "User record of Id: "+id+" is deleted successfully";
            return new  ResponseEntity<>(message,HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body("User not found with id: " + id);
        }
    }
}
