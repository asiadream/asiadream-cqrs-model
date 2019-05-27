package com.asiadream.cqrs.command.process;

import com.asiadream.cqrs.command.model.Account;
import com.asiadream.cqrs.command.model.AccountTransaction;
import com.asiadream.cqrs.command.model.DomainEventPublisher;
import com.asiadream.cqrs.command.repository.AccountRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransactionProcess {
    //
    private final AccountRepository accountRepository;
    private final DomainEventPublisher domainEventPublisher;

    public TransactionProcess(AccountRepository accountRepository, DomainEventPublisher domainEventPublisher) {
        this.accountRepository = accountRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Transactional
    public void trade(UUID accountId, BigDecimal amount) {
        //
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalStateException("Cannot find account with id " + accountId));
        account.makeTransaction(amount);
        domainEventPublisher.publish(new AccountTransaction(accountId, amount));
    }
}
