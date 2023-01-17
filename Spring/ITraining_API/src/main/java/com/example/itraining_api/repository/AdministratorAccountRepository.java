package com.example.itraining_api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.itraining_api.entity.AdministratorAccount;

public interface AdministratorAccountRepository extends CrudRepository<AdministratorAccount, Long> {

    List<AdministratorAccount> findByEmail(String email);
}
