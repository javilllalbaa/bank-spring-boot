package com.banck.bank.services;

import com.banck.bank.controllers.AccountController;
import com.banck.bank.entities.Account;
import com.banck.bank.entities.Transactions;
import com.banck.bank.exceptions.AppException;
import com.banck.bank.repositories.AccountRepository;
import com.banck.bank.repositories.TransactiosRepository;
import com.banck.bank.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@Service
public class TransactionsService {
    private final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private TransactiosRepository transactiosRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Transactions saveTransactions(String estado, Transactions transactions, BigDecimal saldo) {
        transactions.setSaldo(saldo);
        transactions.setTimestamp(Util.getFechaActual());
        transactions.setEstadoTransaccion(estado);
        transactiosRepository.save(transactions);
        return transactions;
    }

    public Optional<Transactions> createTransactions(Transactions transactions) {
        logger.trace("Metodo service createTransactions");
        if(transactions.getTipoTransaccion().contains("Débito")) {
            Account account = accountRepository.getAccount(transactions.getCuenta());
            if(account.getSaldo().compareTo(BigDecimal.ZERO) > 0) {
                if(account.getSaldo().compareTo(transactions.getMonto()) > 0) {
                    BigDecimal operacion = account.getSaldo().subtract(transactions.getMonto());
                    account.setSaldo(operacion);
                    accountRepository.save(account);
                    saveTransactions("Aprobado", transactions, operacion);
                    return Optional.ofNullable(transactions);
                }else {
                    saveTransactions("Rechazado", transactions, account.getSaldo());
                    throw new AppException("El valor a débitar supera al saldo inicial.", NOT_ACCEPTABLE);
                }
            }else {
                saveTransactions("Rechazado", transactions, account.getSaldo());
                throw new AppException("Su saldo es insuficiente.", NOT_ACCEPTABLE);
            }
        }else if(transactions.getTipoTransaccion().contains("Crédito")) {
            Account account = accountRepository.getAccount(transactions.getCuenta());
            if(transactions.getMonto().compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal operacion = account.getSaldo().add(transactions.getMonto());
                account.setSaldo(operacion);
                accountRepository.save(account);
                saveTransactions("Aprobado", transactions, operacion);
                return Optional.ofNullable(transactions);
            }else {
                saveTransactions("Rechazado", transactions, account.getSaldo());
                throw new AppException("El valor a creditar debe ser mayor a cero.", NOT_ACCEPTABLE);
            }
        }else {
            throw new AppException("El tipo de transacción no es valido.", NOT_ACCEPTABLE);
        }

    }

    public List<Transactions> getListTransactios() {
        logger.trace("Metodo Service getListTransactios");
        return transactiosRepository.getListTransactios();
    }

}
