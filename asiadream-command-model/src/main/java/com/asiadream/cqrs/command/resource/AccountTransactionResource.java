package com.asiadream.cqrs.command.resource;

import com.asiadream.cqrs.command.process.TransactionProcess;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/transactions")
public class AccountTransactionResource {
    //
    private final TransactionProcess transactionProcess;

    AccountTransactionResource(TransactionProcess transactionProcess) {
        this.transactionProcess = transactionProcess;
    }

    @PostMapping
    ResponseEntity trade(@RequestBody TransactionalCommand transactionalCommand) {
        transactionProcess.trade(transactionalCommand.getAccountId(), transactionalCommand.getAmount());
        return ResponseEntity.ok().build();
    }
}
