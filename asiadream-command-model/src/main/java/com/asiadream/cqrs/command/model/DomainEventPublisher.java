package com.asiadream.cqrs.command.model;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
