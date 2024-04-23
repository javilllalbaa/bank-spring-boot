package com.banck.bank.controllers;

import com.banck.bank.entities.Account;
import com.banck.bank.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {
    private final Logger logger= LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseBody
    public String ListAccount() {
        return "Hola mundo";
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseEntity<Account> saveUser(@RequestBody Account account) {
        logger.trace("Entering create() with {}", account);
        return accountService.createAccount(account).map(newAccount -> new ResponseEntity<>(newAccount, OK))
                .orElse(new ResponseEntity<>(CONFLICT));
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Account>> listAccount(){
        List<Account> listAccount = accountService.getListAccount();
        if(listAccount.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listAccount,OK);
    }

}
