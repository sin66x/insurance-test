package com.behnam.insurancetest;

import com.behnam.insurancetest.datareader.Event;
import com.behnam.insurancetest.datareader.EventType;
import com.behnam.insurancetest.eventprocessor.ResultCalculator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ResultCalculatorTest {

    private final int YEAR_MONTHS = 12;


    private static ResultCalculator resultCalculator;

    @BeforeAll
    public static void setup() {
        resultCalculator = new ResultCalculator();
        initiateEvents();
    }

    private static void initiateEvents() {

    }

    @Test
    void shouldCreateContract() {
        resultCalculator.addContract(EventInitiator.startEvent);
        for (int month = 0; month < YEAR_MONTHS; month++)
            assertNotNull(resultCalculator.getActiveContracts(month, EventInitiator.startEvent.getContractId()));
    }

    @Test
    void shouldRemoveContract() {
        resultCalculator.addContract(EventInitiator.startEvent);
        resultCalculator.removeContract(EventInitiator.terminateEvent);
        for (int month = 0; month < 2; month++)
            assertNotNull(resultCalculator.getActiveContracts(month, EventInitiator.startEvent.getContractId()));
        assertNull(resultCalculator.getActiveContracts(11, EventInitiator.startEvent.getContractId()));
    }

    @Test
    void shouldIncreaseContract() {
        resultCalculator.addContract(EventInitiator.startEvent);
        resultCalculator.increasePrice(EventInitiator.increaseEvent);
        for (int month = 0; month < 2; month++)
            assertEquals(resultCalculator.getActiveContracts(month, EventInitiator.startEvent.getContractId()).getPremium(),100L);
        for (int month = 3; month < 11; month++)
            assertEquals(resultCalculator.getActiveContracts(month, EventInitiator.startEvent.getContractId()).getPremium(),200L);

    }

    @Test
    void shouldDecreaseContract() {
        resultCalculator.addContract(EventInitiator.startEvent);
        resultCalculator.decreasePrice(EventInitiator.decreaseEvent);
        for (int month = 0; month < 3; month++)
            assertEquals(resultCalculator.getActiveContracts(month, EventInitiator.startEvent.getContractId()).getPremium(),100L);
        for (int month = 4; month < 11; month++)
            assertEquals(resultCalculator.getActiveContracts(month, EventInitiator.startEvent.getContractId()).getPremium(),99L);
    }

    @Test
    void shouldCalculate() {
        resultCalculator.addContract(EventInitiator.startEvent);
        resultCalculator.increasePrice(EventInitiator.increaseEvent);
        resultCalculator.decreasePrice(EventInitiator.decreaseEvent);
        resultCalculator.removeContract(EventInitiator.terminateEvent);
        assertEquals(resultCalculator.calculate().get(5).getAgwp(),997);
        assertEquals(resultCalculator.calculate().get(6).getAgwp(),1196);
        assertEquals(resultCalculator.calculate().get(5).getEgwp(),2191);
    }
}
