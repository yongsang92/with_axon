package com.with.axon.multi_entity_aggregates.event;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreditCardCreatedEvt {
    String cardId;
    int limit;
}
