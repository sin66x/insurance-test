package com.behnam.insurancetest.eventprocessor;

import com.behnam.insurancetest.datareader.Event;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResultCalculator {
    private final int YEAR_MONTHS = 12;

    Map<Integer, Map<String,ContractDetail>> activeContract = new HashMap<>();

    public ContractDetail getActiveContracts(int month,String id){
        return activeContract.get(month).get(id);
    }

    /**
     * initiating the activeContract
     */
    public ResultCalculator() {
        for (int i = 0; i < YEAR_MONTHS; i++)
            activeContract.put(i, new HashMap<String,ContractDetail>());
    }

    /**
     * Insert the contract to all future months
     * @param event
     */
    public void addContract(Event event) {
        for (int availableContractMonth = event.eventMonth()-1; availableContractMonth < YEAR_MONTHS; availableContractMonth++)
            activeContract.get(availableContractMonth).put(event.getContractId(),event.getContractDetail());
    }

    /**
     * Remove the contract from all future months
     * @param event
     */
    public void removeContract(Event event) {
        for (int removingContractDetailMonth = event.eventMonth() ; removingContractDetailMonth < YEAR_MONTHS; removingContractDetailMonth++) {
            activeContract.get(removingContractDetailMonth).remove(event.getContractId());
        }
    }

    /**
     * Increase the contract price for all future months
     * @param event
     */
    public void increasePrice(Event event) {
        for (int increasingMonth = event.eventMonth()-1; increasingMonth < YEAR_MONTHS; increasingMonth++)
            activeContract.get(increasingMonth).get(event.getContractId()).premium += event.getPremiumIncrease();
    }


    /**
     * Decrease the contract price for all future months
     * @param event
     */
    public void decreasePrice(Event event) {
        for (int decreasingMonth = event.eventMonth()-1; decreasingMonth < YEAR_MONTHS; decreasingMonth++)
            activeContract.get(decreasingMonth).get(event.getContractId()).premium -= event.getPremiumReduction();
    }

    /**
     * calculating EGWP, AGWP
     * @return Map<Integer, MonthResult> Key is Month Number (Zero-Index), Value is calculated
     * amounts for the month
     */
    public Map<Integer, MonthResult> calculate() {
        Map<Integer, MonthResult> result = new HashMap<>();
        long agwp = 0;

        for (int month = 0; month < YEAR_MONTHS; month++) {
            long egwp = agwp;
            for (ContractDetail contractDetail : activeContract.get(month).values()) {
                agwp = agwp + contractDetail.premium;
                egwp = agwp + contractDetail.premium*(YEAR_MONTHS-month-1);
            }
            result.put(month, MonthResult.builder().agwp(agwp).egwp(egwp).contractsCount(activeContract.get(month).size()).build());
        }
        return result;
    }
}
