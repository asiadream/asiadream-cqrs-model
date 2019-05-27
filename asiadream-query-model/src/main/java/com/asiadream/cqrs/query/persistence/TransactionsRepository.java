package com.asiadream.cqrs.query.persistence;

import com.asiadream.cqrs.query.resource.TransactionDto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TransactionsRepository extends ReactiveCrudRepository<TransactionDto, String> {
    Flux<TransactionDto> findAllByAccountId(String accountId);
}
