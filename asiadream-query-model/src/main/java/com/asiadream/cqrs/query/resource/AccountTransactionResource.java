package com.asiadream.cqrs.query.resource;

import com.asiadream.cqrs.query.persistence.TransactionsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.websocket.server.PathParam;

@RestController("/transactions")
public class AccountTransactionResource {
    //
    private final TransactionsRepository transactionsRepository;

    AccountTransactionResource(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    @GetMapping
    Flux<TransactionDto> transactions(@PathParam("accountId") String accountId) {
        return transactionsRepository.findAllByAccountId(accountId);
    }

}
