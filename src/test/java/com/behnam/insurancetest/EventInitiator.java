package com.behnam.insurancetest;

import com.behnam.insurancetest.datareader.Event;
import com.behnam.insurancetest.datareader.EventType;

public class EventInitiator {
    public static final String startDate = "2022-01-01";
    public static final String terminateDate = "2022-11-30";
    public static final String increaseDate = "2022-03-20";
    public static final String decreaseDate = "2022-04-20";


    public static Event startEvent;
    public static Event terminateEvent;
    public static Event increaseEvent;
    public static Event decreaseEvent;
    static{
        startEvent = Event.builder()
                .name(EventType.ContractCreatedEvent)
                .contractId("1")
                .startDate(startDate)
                .premium(100L)
                .build();
        terminateEvent = Event.builder()
                .name(EventType.ContractTerminatedEvent)
                .contractId("1")
                .terminationDate(terminateDate)
                .build();
        increaseEvent = Event.builder()
                .contractId("1")
                .name(EventType.PriceIncreasedEvent)
                .premiumIncrease(100L)
                .atDate(increaseDate)
                .build();
        decreaseEvent = Event.builder()
                .name(EventType.PriceDecreasedEvent)
                .contractId("1")
                .premiumReduction(1L)
                .atDate(decreaseDate)
                .build();
    }
}
