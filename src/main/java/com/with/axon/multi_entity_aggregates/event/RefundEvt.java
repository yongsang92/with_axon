package com.with.axon.multi_entity_aggregates.event;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class RefundEvt implements Serializable{
    final String cardId; // 사용자 카드 아이디
    final String transactionId; // 결재한날의 고유 아이디
    LocalDateTime date; // 결제한 날
}
