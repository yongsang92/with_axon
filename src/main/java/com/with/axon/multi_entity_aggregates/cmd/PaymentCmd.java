package com.with.axon.multi_entity_aggregates.cmd;

import java.time.LocalDateTime;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCmd {
    @TargetAggregateIdentifier
    String cardId;
    int value;
    // LocalDateTime today;
}
