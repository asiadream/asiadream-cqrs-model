package com.asiadream.cqrs.command.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

@Entity
public class StoredDomainEvent {
    //
    @Id String id;
    private String content;
    private boolean sent;
    private Instant eventTimestamp = Instant.now();
    private String eventType;

    public StoredDomainEvent(String content, String eventType) {
        this.content = content;
        this.id = UUID.randomUUID().toString();
        this.eventType = eventType;
    }

    private StoredDomainEvent() {

    }

    public void sent() {
        this.sent = true;
    }

    public String getContent() {
        return this.content;
    }

    public String getEventType() {
        return this.eventType;
    }

}
