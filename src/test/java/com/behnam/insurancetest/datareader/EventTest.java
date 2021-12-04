package com.behnam.insurancetest.datareader;

import com.behnam.insurancetest.EventInitiator;
import com.behnam.insurancetest.eventprocessor.ContractDetail;
import com.behnam.insurancetest.exceptions.BadDateFormat;
import com.behnam.insurancetest.exceptions.NonDatedEventException;
import com.behnam.insurancetest.exceptions.ReadJsonException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {


    @BeforeAll
    static void setup(){

    }

    @Test
    void shouldCreateContractDetail() {
        ContractDetail contractDetail = EventInitiator.startEvent.getContractDetail();
        assertEquals(contractDetail.getContractId(),EventInitiator.startEvent.getContractId());
        assertEquals(contractDetail.getPremium(),EventInitiator.startEvent.getPremium());
    }

    @Test
    void shouldReturnEventDate() {
        assertEquals(EventInitiator.startEvent.getEventDate(),EventInitiator.startDate);
        assertEquals(EventInitiator.terminateEvent.getEventDate(),EventInitiator.terminateDate);
        assertEquals(EventInitiator.increaseEvent.getEventDate(),EventInitiator.increaseDate);
        assertEquals(EventInitiator.decreaseEvent.getEventDate(),EventInitiator.decreaseDate);
    }

    @Test
    void shouldReturnEventMonth() {
        assertEquals(EventInitiator.startEvent.eventMonth(),1);
        assertEquals(EventInitiator.terminateEvent.eventMonth(),11);
        assertEquals(EventInitiator.increaseEvent.eventMonth(),3);
        assertEquals(EventInitiator.decreaseEvent.eventMonth(),4);
    }

    @Test
    void shouldEventMonthThrowNonDatedEventException_nullDate() {
        assertThrows(NonDatedEventException.class, () -> {
            Event event = Event.builder().build();
            event.eventMonth();
        });
    }

    @Test
    void shouldEventMonthThrowBadDateFormat_wrongDateFormat() {
        assertThrows(BadDateFormat.class, () -> {
            Event event = Event.builder().startDate("2020/02/01").build();
            event.eventMonth();
        });
    }

}