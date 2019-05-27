package com.asiadream.cqrs.query.process;

import com.asiadream.cqrs.query.model.AccountTransaction;
import com.asiadream.cqrs.query.persistence.TransactionsRepository;
import com.asiadream.cqrs.query.resource.TransactionDto;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class ReadModelUpdater {
    //
    private final TransactionsRepository transactionsRepository;

    ReadModelUpdater(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    @StreamListener(target = Sink.INPUT, condition = "headers['type'] == 'account-transaction'")
    public void handle(AccountTransaction accountTransaction) {
        transactionsRepository
                .save(new TransactionDto(UUID.randomUUID().toString(), accountTransaction.getAccountId().toString(), accountTransaction.getAmount())).subscribe();
    }
}
