package com.example.itraining_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.ParameterOutOfBoundsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class LoginController {

    @Autowired
    private LearnerAccountRepository learnerAccountRepository;

    @Autowired
    private TeacherAccountRepository teacherAccountRepository;

    @Autowired
    private AdministratorAccountRepository administratorAccountRepository;

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    @PostMapping("/createAccount")
    public ResponseEntity<String> createLearnerAccount(@RequestBody UserAccount userAccount,
            @RequestParam String accountType) {
        UserAccount savedUser = null;
        ResponseEntity<String> response = null;
        try {
            // String hashPwd = passwordEncoder.encode(teacherAccount.getPassword());
            // teacherAccount.setPassword(hashPwd);
            switch (accountType) {
                case "learner":
                    savedUser = learnerAccountRepository.save((LearnerAccount) userAccount);
                    break;
                case "teacher":
                    savedUser = teacherAccountRepository.save((TeacherAccount) userAccount);
                    break;
                case "administrator":
                    savedUser = administratorAccountRepository.save((AdministratorAccount) userAccount);
                    break;
                default:
                    throw new IllegalArgumentException(
                            "accountType should be one of the following [learner, teacher, administrator]");
            }
            if (savedUser.getId() > 0) {
                response = ResponseEntity.status(HttpStatus.CREATED)
                        .body("Learner account has succefully been created");
            }
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception has occured due to " + e.getMessage());
        }
        return response;
    }

    // @PostMapping("/login")
    // public ResponseEntity<String> loginUser(@RequestParam String email,
    // @RequestParam String password) {

    // }

}