package com.with.axon.multi_entity_aggregates.aggregates;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.naming.LimitExceededException;

import com.with.axon.multi_entity_aggregates.aggregates.entity.CreditCardTransaction;
import com.with.axon.multi_entity_aggregates.cmd.CreditCardCreatedCmd;
import com.with.axon.multi_entity_aggregates.cmd.PaymentCmd;
import com.with.axon.multi_entity_aggregates.event.CreditCardCreatedEvt;
import com.with.axon.multi_entity_aggregates.event.PaymentSuccessEvt;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@NoArgsConstructor
@Log4j2
@Aggregate 
public class CreditCard {

    @AggregateIdentifier
    private String cardId;

    @AggregateMember
    private List<CreditCardTransaction> transactions= new ArrayList<>();
 
    private int limit; // 100만원 이상은 결제 못하게 막아놓는다 // 누적금액이 아니다

    @CommandHandler
    public CreditCard(CreditCardCreatedCmd cmd) {
        log.info(cmd.getCardId()+","+cmd.getLimit());
       apply(new CreditCardCreatedEvt (cmd.getCardId(), cmd.getLimit())); // 아이디, 금액
    }

    @EventSourcingHandler
    public void on(CreditCardCreatedEvt evt) {
        cardId = evt.getCardId();
        limit = evt.getLimit();
        log.info("[CreditCardCreatedEvt] id : "+cardId);
    }

    @CommandHandler
    public void on(PaymentCmd cmd) throws LimitExceededException {
        if (limit < cmd.getValue())
            throw new LimitExceededException();
        // 한도를 초과해서 예외를 던진다
        // 인터셉터로 예외를 가로채야 할까?

        apply(new PaymentSuccessEvt(UUID.randomUUID().toString(), cmd.getValue(),LocalDateTime.now())); 
        // 단순히 결제에 대한 ID를 전달한다(랜덤으로 생성된), 결제금액
        // 이 ID를 이용해서 취소를 할 수 있어야한다. 그렇다면 사용자가 이 ID를 알고 있어야 하겠지? 어떻게든
    }

    @EventSourcingHandler
    public void handle(PaymentSuccessEvt evt) {
        log.info("[PaymentSuccessEvt] transactionId : "+evt.getTransactionId());
        transactions.add(new CreditCardTransaction(evt.getTransactionId(), evt.getValue(),evt.getDate()));
    }

}
