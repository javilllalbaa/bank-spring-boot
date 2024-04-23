package com.banck.bank.controllers;

import com.banck.bank.entities.Transactions;
import com.banck.bank.services.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactiosController {
    private final Logger logger= LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private TransactionsService transactionsService;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Transactions>> listAccount(){
        List<Transactions> listTransactios = transactionsService.getListTransactios();
        if(listTransactios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listTransactios,OK);
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseEntity<Transactions> saveUser(@RequestBody Transactions transactions) {
        logger.trace("Entering create() with {}", transactions);
        return transactionsService.createTransactions(transactions).map(newCustomer -> new ResponseEntity<>(newCustomer, OK))
                .orElse(new ResponseEntity<>(CONFLICT));
    }

}
