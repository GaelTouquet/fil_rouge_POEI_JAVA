package com.example.itraining_api.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.itraining_api.entity.AdministratorAccount;

public interface AdministratorAccountRepository extends CrudRepository<AdministratorAccount, Long> {
    
}
