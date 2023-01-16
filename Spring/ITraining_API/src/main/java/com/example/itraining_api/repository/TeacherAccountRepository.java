package com.example.itraining_api.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.itraining_api.entity.TeacherAccount;

public interface TeacherAccountRepository extends CrudRepository<TeacherAccount, Long> {
}
