package com.with.axon.multi_entity_aggregates.event;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentSuccessEvt implements Serializable {
    String transactionId;
    int value;
    LocalDateTime date;
}
