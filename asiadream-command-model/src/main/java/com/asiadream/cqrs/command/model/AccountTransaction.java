package com.asiadream.cqrs.command.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class AccountTransaction implements DomainEvent {
    //
    private UUID accountId;
    private BigDecimal amount;
    private Instant timestamp = Instant.now();

    public AccountTransaction(UUID accountId, BigDecimal amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    @Override
    public String getType() {
        return "account-transaction";
    }
}
