package com.with.axon.multi_entity_aggregates.aggregates.entity;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.with.axon.multi_entity_aggregates.cmd.RefundCmd;
import com.with.axon.multi_entity_aggregates.event.RefundEvt;
import com.with.axon.multi_entity_aggregates.exception.DueTimeOverException;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import org.axonframework.modelling.command.EntityId;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreditCardTransaction implements Serializable{

    @EntityId
    private String transactionId;

    private int transactionValue;
    private boolean refund = false; // 취소여부

    private LocalDateTime date; // 결제날짜


    public CreditCardTransaction(String transactionId, int transactionValue,LocalDateTime date) {
        this.transactionId = transactionId;
        this.transactionValue = transactionValue;
        this.date = date;
    }

    @CommandHandler
    public void on(RefundCmd cmd) {

        if (ChronoUnit.DAYS.between(date, cmd.getDate()) >= 30)
            throw new DueTimeOverException();
        // 30일이 지났는데 환불을 시도하면 예외를 던진다
        log.info("[RefundCmd] " + cmd.getTransactionId());
        apply(new RefundEvt(cmd.getCardId(), cmd.getTransactionId(),cmd.getDate()));
    }
    
   

    @EventSourcingHandler
    public void hanlde(RefundEvt evt) {

        if (transactionId.equals(evt.getTransactionId())) {
            transactionValue *= -1; // 환불하기 때문에 음수를 곱해준다
            date = LocalDateTime.now(); // 결제 날짜 업데이트
            refund = true;
            log.info("[RefundEvt accepted] transactionId : " + transactionId);
            return;
        }
        log.info("[RefundEvt rejected] transactionId : " + transactionId);
    }

}