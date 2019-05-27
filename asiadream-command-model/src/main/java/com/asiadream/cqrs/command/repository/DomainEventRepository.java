package com.asiadream.cqrs.command.repository;

import com.asiadream.cqrs.command.model.StoredDomainEvent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DomainEventRepository extends CrudRepository<StoredDomainEvent, Long> {
    List<StoredDomainEvent> findAllBySentOrderByEventTimestampDesc(boolean sent);
}
