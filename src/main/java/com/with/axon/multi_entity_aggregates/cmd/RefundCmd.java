package com.with.axon.multi_entity_aggregates.cmd;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundCmd implements Serializable{
    @TargetAggregateIdentifier
    String cardId; // 사용자 카드 아이디
    String transactionId; // 결재한날의 고유 아이디
    LocalDateTime date;
}
