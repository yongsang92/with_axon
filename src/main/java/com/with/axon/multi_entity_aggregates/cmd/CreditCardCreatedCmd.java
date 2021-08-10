package com.with.axon.multi_entity_aggregates.cmd;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreditCardCreatedCmd {
    @TargetAggregateIdentifier
    String cardId;
    int limit;
}
