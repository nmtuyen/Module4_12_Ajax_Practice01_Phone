package com.codegym.controller;

import com.codegym.model.User;
import com.codegym.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private IUserService userService;

    // Show list user
    @GetMapping("")
    public ResponseEntity<Iterable<User>> findAll(){
        return new ResponseEntity<>(userService.findALl(), HttpStatus.OK);
    }

    // Add new user
    @PostMapping
    public ResponseEntity<User> saveNew(@RequestBody User user){
        if (userService.findByUserName(user.getUserName()).isPresent()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userService.save(user);
        return new ResponseEntity<>(userService.findById(user.getId()).get(), HttpStatus.CREATED);
    }

    // Update user
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user){
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (user.getUserName().equals("")){
            user.setUserName(userOptional.get().getUserName());
        }
        if (user.getPassword().equals("")){
            user.setPassword(userOptional.get().getPassword());
        }
        if (user.getGmail().equals("")){
            user.setGmail(userOptional.get().getGmail());
        }
        user.setId(id);
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id){
        if (!userService.findById(id).isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        userService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK );
    }

}
