package com.asiadream.cqrs.command.publisher;

import com.asiadream.cqrs.command.model.DomainEvent;
import com.asiadream.cqrs.command.model.DomainEventPublisher;
import com.asiadream.cqrs.command.model.StoredDomainEvent;
import com.asiadream.cqrs.command.repository.DomainEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaDomainEventPublisher implements DomainEventPublisher {
    //
    private final Source source;
    private final DomainEventRepository domainEventRepository;
    private final ObjectMapper objectMapper;

    public KafkaDomainEventPublisher(Source source, DomainEventRepository domainEventRepository, ObjectMapper objectMapper) {
        this.source = source;
        this.domainEventRepository = domainEventRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publish(DomainEvent event) {
        //
        try {
            domainEventRepository.save(new StoredDomainEvent(objectMapper.writeValueAsString(event), event.getType()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(fixedRate = 2000)
    @Transactional
    public void publishExternally() {
        //
        domainEventRepository
                .findAllBySentOrderByEventTimestampDesc(false)
                .forEach(storedDomainEvent -> {
                    Map<String, Object> headers = new HashMap<>();
                    headers.put("type", storedDomainEvent.getEventType());
                    source.output().send(new GenericMessage<>(storedDomainEvent.getContent(), headers));
                    storedDomainEvent.sent();
                });
    }
}

