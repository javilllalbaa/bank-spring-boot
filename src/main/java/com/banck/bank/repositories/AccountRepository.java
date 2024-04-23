package com.banck.bank.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.banck.bank.controllers.AccountController;
import com.banck.bank.entities.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccountRepository {
    private final Logger logger= LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Account save(Account account) {
        logger.trace("Metodo SaveAccount");
        dynamoDBMapper.save(account);
        return account;
    }

    public List<Account> getListAccount(){
        logger.trace("Metodo getListAccount");
        PaginatedList<Account> resultsAccount= dynamoDBMapper.scan(Account.class, new DynamoDBScanExpression());
        resultsAccount.loadAllResults();
        return resultsAccount;
    }

    public Account getAccount(String cuenta) {
        logger.trace("Metodo getAccount");
        Account account = null;
        Map<String, AttributeValue> eav= new HashMap<String , AttributeValue>();
        eav.put(":Cuenta", new AttributeValue().withS(cuenta));
        DynamoDBScanExpression scanExpression=new DynamoDBScanExpression()
                .withFilterExpression("Cuenta = :Cuenta")
                .withExpressionAttributeValues(eav);
        List<Account> accountResult = dynamoDBMapper.scan(Account.class, scanExpression);
        if(!accountResult.isEmpty() && accountResult.size() > 0) {
            account = accountResult.get(0);
        }
        return account;
    }

}
