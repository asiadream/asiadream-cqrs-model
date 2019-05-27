package com.asiadream.cqrs.command.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Account {
    //
    @Id
    @GeneratedValue @Getter
    private UUID id;
    @Getter
    private BigDecimal balance;

    public Account(BigDecimal initialAmount) {
        this.balance = initialAmount;
    }

    public void makeTransaction(BigDecimal amount) {
        if (isThereEnoughMoney(amount)) {
            this.balance = balance.add(amount);
        } else {
            throw new NotEnoughMoneyException(id, amount, balance);
        }
    }

    private boolean isThereEnoughMoney(BigDecimal amount) {
        return balance.add(amount).compareTo(BigDecimal.ZERO) >= 0;
    }

}
