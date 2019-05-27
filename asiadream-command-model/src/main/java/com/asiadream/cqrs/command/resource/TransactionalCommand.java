package com.asiadream.cqrs.command.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionalCommand {
    private UUID accountId;
    private BigDecimal amount;
}
