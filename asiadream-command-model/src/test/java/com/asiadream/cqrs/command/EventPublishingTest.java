package com.asiadream.cqrs.command;

import com.asiadream.cqrs.command.model.Account;
import com.asiadream.cqrs.command.model.AccountTransaction;
import com.asiadream.cqrs.command.repository.AccountRepository;
import com.asiadream.cqrs.command.resource.TransactionalCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventPublishingTest {
    //
    @Autowired AccountRepository accountRepository;
    @Autowired TestRestTemplate restTemplate;
    @Autowired ObjectMapper objectMapper;
    @Autowired MessageCollector messageCollector;
    @Autowired Source source;

    BlockingQueue<Message<?>> events;

    @Before
    public void setup() {
        events = messageCollector.forChannel(source.output());
    }

    @Test
    public void shouldEventuallySendAnEventAboutAccountTransaction() {
        // given
        UUID accountUUid = thereIsAnAccount(BigDecimal.TEN);
        // when
        clientWantsToTrade(BigDecimal.TEN, accountUUid);

        // then
        Awaitility.await().atMost(Duration.FIVE_SECONDS).until(() -> wasTransactionSent(BigDecimal.TEN, accountUUid));
    }

    private UUID thereIsAnAccount(BigDecimal balance) {
        Account account = new Account(balance);
        return accountRepository.save(account).getId();
    }

    private void clientWantsToTrade(BigDecimal amount, UUID accountUUid) {
        restTemplate.postForEntity("/transactions", new TransactionalCommand(accountUUid, amount), Void.class);
    }

    private boolean wasTransactionSent(BigDecimal amount, UUID accountUUid) throws IOException {
        Message msg = events.poll();
        if (msg == null) {
            System.out.println("message is null...");
            return false;
        }

        String payload = msg.getPayload().toString();
        System.out.println("payload --> " + payload);
        AccountTransaction event = objectMapper.readValue(payload, AccountTransaction.class);
        return event.getAmount().compareTo(amount) == 0 && event.getAccountId().equals(accountUUid);
    }
}
