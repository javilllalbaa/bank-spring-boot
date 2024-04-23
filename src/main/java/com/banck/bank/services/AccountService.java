package com.banck.bank.services;

import com.banck.bank.controllers.AccountController;
import com.banck.bank.entities.Account;
import com.banck.bank.repositories.AccountRepository;
import com.banck.bank.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountRepository accountRepository;

    public Optional<Account> createAccount(Account account) {
        logger.trace("Metodo service createUser");
        account.setFechaApertura(Util.getFechaActual());
        accountRepository.save(account);
        return Optional.of(account);
    }

    public List<Account> getListAccount() {
        logger.trace("Metodo Service getListAccount");
        return accountRepository.getListAccount();
    }

}
