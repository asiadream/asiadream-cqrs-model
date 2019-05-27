package com.asiadream.cqrs.command.model;

import java.math.BigDecimal;
import java.util.UUID;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(UUID id, BigDecimal amount, BigDecimal balance) {
    }
}
