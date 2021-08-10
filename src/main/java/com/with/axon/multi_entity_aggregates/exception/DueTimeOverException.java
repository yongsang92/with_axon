package com.with.axon.multi_entity_aggregates.exception;
public class DueTimeOverException extends IllegalStateException {

    public DueTimeOverException() {
        super("Refund period is over");
    }
}
