package com.example.itraining_api.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.itraining_api.entity.UserAccount;

@RestController
abstract class LoginController {

    @Autowired
    private CrudRepository<UserAccount, Long> crudRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccountUser(@RequestBody UserAccount user){
        UserAccount savedUser = null;
        ResponseEntity<String> response = null;
        try {
            String hashPwd = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashPwd);
            savedUser = crudRepository.save(user);
            if(savedUser.getId()>0){
                response = ResponseEntity.status(HttpStatus.CREATED).body("User has succefully been created");
            }
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An exception has occured due to " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password){
        // TODO
    }

}