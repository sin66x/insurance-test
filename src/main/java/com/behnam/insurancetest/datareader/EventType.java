package com.behnam.insurancetest.datareader;

/**
 * Event names are from one of these types
 */
public enum EventType {
    PriceDecreasedEvent("PriceDecreasedEvent"),
    ContractTerminatedEvent("ContractTerminatedEvent"),
    ContractCreatedEvent("ContractCreatedEvent"),
    PriceIncreasedEvent("PriceIncreasedEvent");

    private final String name;

    EventType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
