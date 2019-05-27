package com.asiadream.cqrs.query.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class AccountTransaction {
    //
    private UUID accountId;
    private BigDecimal amount;
    private Instant timestamp = Instant.now();

    public AccountTransaction(UUID accountId, BigDecimal amount) {
        this.accountId = accountId;
        this.amount = amount;
    }
}
