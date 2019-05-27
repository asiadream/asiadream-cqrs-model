package com.asiadream.cqrs.command.model;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class AccountTest {

    @Test
    public void cannotBeTradedWhenNotEnoughMoney() {
        // given
        Account account = new Account(BigDecimal.TEN);

        // expect
        assertThatExceptionOfType(NotEnoughMoneyException.class)
                .isThrownBy(() -> account.makeTransaction(new BigDecimal(-11)));
    }

    @Test
    public void canBeTradedWithEnoughMoney() {
        // given
        Account account = new Account(BigDecimal.TEN);

        // when
        account.makeTransaction(BigDecimal.ONE);

        // expect
        assertThat(account.getBalance())
                .isEqualByComparingTo(new BigDecimal(11));
    }
}
