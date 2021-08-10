package com.with.axon.multi_entity_aggregates.api;
import java.time.LocalDateTime;

import com.with.axon.multi_entity_aggregates.cmd.CreditCardCreatedCmd;
import com.with.axon.multi_entity_aggregates.cmd.PaymentCmd;
import com.with.axon.multi_entity_aggregates.cmd.RefundCmd;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class Api {

    private final CommandGateway commandGateway;

    @PostMapping("/createCreditCard/{cardId}/{limit}")
    public void createCreditCard(@PathVariable String cardId, @PathVariable int limit) {
        commandGateway.send(new CreditCardCreatedCmd(cardId, limit));
    }
    // 신용카드를 만든다

    
    @PostMapping("/paymentService/{cardId}/{value}")
    public void paymentService(@PathVariable String cardId, @PathVariable int value) {
        commandGateway.send(new PaymentCmd(cardId, value));
    }
    // 결제를 신청한다


    @PostMapping("/refundService/{cardId}/{transactionId}")
    public String paymentService(@PathVariable String cardId, @PathVariable String transactionId) {
        commandGateway.send(new RefundCmd(cardId, transactionId,LocalDateTime.now()));
        return cardId+","+transactionId;
    }
    // 환불을 신청한다
}
