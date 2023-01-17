package com.example.itraining_api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.itraining_api.entity.LearnerAccount;

public interface LearnerAccountRepository extends CrudRepository<LearnerAccount, Long> {

    List<LearnerAccount> findByEmail(String email);
}
