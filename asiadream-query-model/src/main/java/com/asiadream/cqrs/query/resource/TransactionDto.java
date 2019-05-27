package com.asiadream.cqrs.query.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class TransactionDto {
    //
    @Id private String id;
    private String accountId;
    private BigDecimal amount;
}
