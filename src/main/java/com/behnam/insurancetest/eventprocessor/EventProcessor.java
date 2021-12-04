package com.behnam.insurancetest.eventprocessor;

import com.behnam.insurancetest.datareader.Event;
import com.behnam.insurancetest.datareader.ReadJson;
import com.behnam.insurancetest.exceptions.NotDefinedEventException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Main Class of the test
 */
@Component
public class EventProcessor {
    private static final Logger logger = LogManager.getLogger(EventProcessor.class);


    private final ReadJson readJson;
    private ResultCalculator resultCalculator;

    @Autowired
    public EventProcessor(ReadJson readJson) {
        this.readJson = readJson;
    }

    /**
     * This function runs both Task1 and Task2 and shows the output in STDOUT
     */
    public void mainAnswer() {
        resultCalculator = new ResultCalculator();
        List<Event> events = readJson.read();
        events = events.stream().sorted(Comparator.comparing(Event::getEventDate)).collect(Collectors.toList());

        Map<Integer, MonthResult> task1Result = task1(events);
        logger.info("-- Task 1:");
        printAnswer(task1Result);

        Map<Integer, MonthResult> task2Result = task2(events);
        logger.info("-- Task 2:");
        printAnswer(task2Result);
    }

    private Map<Integer, MonthResult> task1(List<Event> events) {
        for (Event event : events) {
            switch (event.getName()) {
                case ContractCreatedEvent:
                    resultCalculator.addContract(event);
                    break;
                case ContractTerminatedEvent:
                    resultCalculator.removeContract(event);
                    break;
                case PriceIncreasedEvent:
                case PriceDecreasedEvent:
                    break;
                default:
                    throw new NotDefinedEventException("Not Defined Event:" + event.getName());
            }
        }
        return resultCalculator.calculate();
    }

    private Map<Integer, MonthResult> task2(List<Event> events) {
        for (Event event : events) {
            switch (event.getName()) {
                case ContractCreatedEvent:
                    resultCalculator.addContract(event);
                    break;
                case ContractTerminatedEvent:
                    resultCalculator.removeContract(event);
                    break;
                case PriceIncreasedEvent:
                    resultCalculator.increasePrice(event);
                    break;
                case PriceDecreasedEvent:
                    resultCalculator.decreasePrice(event);
                    break;
                default:
                    throw new NotDefinedEventException("Not Defined Event:" + event.getName());
            }
        }
        return resultCalculator.calculate();
    }

    private void printAnswer(Map<Integer, MonthResult> taskResult) {
        for (int i = 0; i < taskResult.size(); i++) {
            logger.info("Month(" + i + "): ContractNo:" + taskResult.get(i).contractsCount + ", AG" + taskResult.get(i).agwp + ", EG" + taskResult.get(i).egwp);
        }
    }

}
