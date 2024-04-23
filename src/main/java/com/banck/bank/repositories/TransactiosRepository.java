package com.banck.bank.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import com.banck.bank.entities.Transactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactiosRepository {

    private final Logger logger= LoggerFactory.getLogger(TransactiosRepository.class);

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Transactions save(Transactions transactions) {
        logger.trace("Metodo SaveTransactions");
        dynamoDBMapper.save(transactions);
        return transactions;
    }

    public List<Transactions> getListTransactios(){
        logger.trace("Metodo getListTransactios");
        PaginatedList<Transactions> resultsTransactions= dynamoDBMapper.scan(Transactions.class, new DynamoDBScanExpression());
        resultsTransactions.loadAllResults();
        return resultsTransactions;
    }

}
