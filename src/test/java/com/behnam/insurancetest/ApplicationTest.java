package com.behnam.insurancetest;

import com.behnam.insurancetest.datareader.Event;
import com.behnam.insurancetest.datareader.EventType;
import com.behnam.insurancetest.datareader.ReadJson;
import com.behnam.insurancetest.eventprocessor.EventProcessor;
import com.behnam.insurancetest.eventprocessor.ResultCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ApplicationTest {

    @Mock
    private ReadJson readJson;

    @Mock
    private ResultCalculator resultCalculator;

    private EventProcessor eventProcessor;

    @BeforeEach
    public void setup() {
        List<Event> events = initiateEventList();
        Mockito.when(readJson.read()).thenReturn(events);
        eventProcessor = new EventProcessor(readJson);
    }

    private List<Event> initiateEventList() {
        List<Event> events = new ArrayList<>();
        events.add(Event.builder().name(EventType.ContractCreatedEvent).startDate("2020-01-01").contractId("1").premium(100L).build());
        events.add(Event.builder().name(EventType.PriceIncreasedEvent).atDate("2020-02-01").contractId("1").premiumIncrease(100L).build());
        events.add(Event.builder().name(EventType.PriceDecreasedEvent).atDate("2020-03-01").contractId("1").premiumReduction(100L).build());
        events.add(Event.builder().name(EventType.ContractTerminatedEvent).terminationDate("2020-04-30").contractId("1").build());
        return events;
    }

    @Test
    void testMain() {
        eventProcessor.mainAnswer();
    }
}
