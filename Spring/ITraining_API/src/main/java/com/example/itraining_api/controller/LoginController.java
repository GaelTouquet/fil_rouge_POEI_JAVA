package com.example.itraining_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.ParameterOutOfBoundsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.itraining_api.entity.AdministratorAccount;
import com.example.itraining_api.entity.LearnerAccount;
import com.example.itraining_api.entity.TeacherAccount;
import com.example.itraining_api.entity.UserAccount;
import com.example.itraining_api.repository.AdministratorAccountRepository;
import com.example.itraining_api.repository.LearnerAccountRepository;
import com.example.itraining_api.repository.TeacherAccountRepository;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LearnerAccountRepository learnerAccountRepository;

    @Autowired
    private TeacherAccountRepository teacherAccountRepository;

    @Autowired
    private AdministratorAccountRepository administratorAccountRepository;

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    @PostMapping("/createLearnerAccount")
    public ResponseEntity<String> createLearnerAccount(@RequestBody LearnerAccount learnerAccount) {
        LearnerAccount savedLearnerAccount = null;
        ResponseEntity<String> response = null;
        try {
            // String hashPwd = passwordEncoder.encode(teacherAccount.getPassword());
            // teacherAccount.setPassword(hashPwd);
            savedLearnerAccount = learnerAccountRepository.save(learnerAccount);
            if (savedLearnerAccount.getId() > 0) {
                response = ResponseEntity.status(HttpStatus.CREATED)
                        .body("Learner account has succefully been created");
            }
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception has occured due to " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/createTeacherAccount")
    public ResponseEntity<String> createTeacherAccount(@RequestBody TeacherAccount teacherAccount) {
        TeacherAccount savedTeacherAccount = null;
        ResponseEntity<String> response = null;
        try {
            // String hashPwd = passwordEncoder.encode(teacherAccount.getPassword());
            // teacherAccount.setPassword(hashPwd);
            savedTeacherAccount = teacherAccountRepository.save(teacherAccount);
            if (savedTeacherAccount.getId() > 0) {
                response = ResponseEntity.status(HttpStatus.CREATED)
                        .body("teacher account has succefully been created");
            }
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception has occured due to " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/createAdministratorAccount")
    public ResponseEntity<String> createAdministratorAccount(@RequestBody AdministratorAccount administratorAccount) {
        AdministratorAccount savedAdministratorAccount = null;
        ResponseEntity<String> response = null;
        try {
            // String hashPwd = passwordEncoder.encode(AdministratorAccount.getPassword());
            // AdministratorAccount.setPassword(hashPwd);
            savedAdministratorAccount = administratorAccountRepository.save(administratorAccount);
            if (savedAdministratorAccount.getId() > 0) {
                response = ResponseEntity.status(HttpStatus.CREATED)
                        .body("Administrator account has succefully been created");
            }
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception has occured due to " + e.getMessage());
        }
        return response;
    }

    // @PostMapping("/login")
    // public ResponseEntity<String> loginUser(@RequestBody String email,
    // @RequestBody String password) {
    //     ResponseEntity<String> response = null;
    //     try {
    //         List<AdministratorAccount> foundAdministratorAccountList = administratorAccountRepository.findByEmail(email);

    //     } catch (Exception e) {
    //         response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //                 .body("An exception has occured due to " + e.getMessage());
    //     }
    // }

}