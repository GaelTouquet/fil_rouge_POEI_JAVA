package com.example.itraining_api.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.itraining_api.entity.AdministratorAccount;
import com.example.itraining_api.entity.LearnerAccount;
import com.example.itraining_api.entity.TeacherAccount;
import com.example.itraining_api.repository.AdministratorAccountRepository;
import com.example.itraining_api.repository.LearnerAccountRepository;
import com.example.itraining_api.repository.TeacherAccountRepository;

public class ItrainingAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    TeacherAccountRepository teacherAccountRepository;

    @Autowired
    AdministratorAccountRepository administratorAccountRepository;

    @Autowired
    LearnerAccountRepository learnerAccountRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<AdministratorAccount> foundAdministratorAccountList = administratorAccountRepository.findByEmail(username);
        if (foundAdministratorAccountList.size() > 0) {
            if (passwordEncoder.matches(password, foundAdministratorAccountList.get(0).getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<GrantedAuthority>() {
                    {
                        add(new SimpleGrantedAuthority("administrator"));
                        add(new SimpleGrantedAuthority("teacher"));
                        add(new SimpleGrantedAuthority("learner"));
                    }
                });
            } else {
                throw new BadCredentialsException("Invalid password");
            }
        } 
        List<TeacherAccount> foundTeacherAccountList = teacherAccountRepository.findByEmail(username);
        if (foundTeacherAccountList.size() > 0) {
            if (passwordEncoder.matches(password, foundTeacherAccountList.get(0).getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<GrantedAuthority>() {
                    {
                        add(new SimpleGrantedAuthority("teacher"));
                        add(new SimpleGrantedAuthority("learner"));
                    }
                });
            } else {
                throw new BadCredentialsException("Invalid password");
            }
        } 
        List<LearnerAccount> foundLearnerAccountList = learnerAccountRepository.findByEmail(username);
        if (foundLearnerAccountList.size() > 0) {
            if (passwordEncoder.matches(password, foundLearnerAccountList.get(0).getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<GrantedAuthority>() {
                    {
                        add(new SimpleGrantedAuthority("learner"));
                    }
                });
            } else {
                throw new BadCredentialsException("Invalid password");
            }
        } 

        else {
            throw new BadCredentialsException("No User Registered with this information");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
