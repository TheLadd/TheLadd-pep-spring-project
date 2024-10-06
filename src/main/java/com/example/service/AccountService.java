package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.exception.InvalidUsernameException;
import com.example.exception.UsernameAlreadyExistsException;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    public Account registerAccount(Account acc) throws InvalidUsernameException, UsernameAlreadyExistsException {
        // Verify that UN is valid
        String un = acc.getUsername();
        if (un.length() < 4) {
           throw new InvalidUsernameException(); 
        }

        // Verify that UN does not already exist
        Optional<Account> optionalAccount = accountRepository.findByUsername(un);
        if (optionalAccount.isPresent()) {
            throw new UsernameAlreadyExistsException();
        }

        // Create and return new account
        return accountRepository.save(acc);
    }

    // TODO
    public Account accountExists(Account acc) {
        return null;
    }
}
