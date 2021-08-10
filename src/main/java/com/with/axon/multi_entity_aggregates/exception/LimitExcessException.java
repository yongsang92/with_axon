package com.with.axon.multi_entity_aggregates.exception;

public class LimitExcessException extends IllegalStateException {

    public LimitExcessException(String limit) {
        super("can't pay over " + limit);
    }
}