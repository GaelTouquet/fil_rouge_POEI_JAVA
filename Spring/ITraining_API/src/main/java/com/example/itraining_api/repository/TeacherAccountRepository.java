package com.example.itraining_api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.itraining_api.entity.TeacherAccount;

public interface TeacherAccountRepository extends CrudRepository<TeacherAccount, Long> {
    List<TeacherAccount> findByEmail(String email);
}
