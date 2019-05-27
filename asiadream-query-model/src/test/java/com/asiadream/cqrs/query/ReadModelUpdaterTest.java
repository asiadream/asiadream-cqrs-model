package com.asiadream.cqrs.query;

import com.asiadream.cqrs.query.model.AccountTransaction;
import com.asiadream.cqrs.query.resource.TransactionDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.http.MediaType;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReadModelUpdaterTest {
    //
    private final UUID accountId = UUID.randomUUID();

    @Autowired
    Sink sink;

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void shouldSeeTransactionAfterGettingAnEvent() {
        // when
        anEventAboutTransactionCame(BigDecimal.TEN, accountId);

        // then
        thereIsOneTransactionOf(BigDecimal.TEN, accountId);
    }

    private void anEventAboutTransactionCame(BigDecimal amount, UUID accountId) {
        //
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", "account-transaction");
        sink.input().send(new GenericMessage<>(new AccountTransaction(accountId, amount), headers));
    }

    private void thereIsOneTransactionOf(BigDecimal amount, UUID accountId) {
        //
        this.webTestClient
                .get()
                .uri("/transactions?accountId=" + accountId)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TransactionDto.class).hasSize(1);
    }
}
