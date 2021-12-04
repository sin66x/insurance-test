package com.behnam.insurancetest.datareader;

import com.behnam.insurancetest.eventprocessor.ContractDetail;
import com.behnam.insurancetest.exceptions.BadDateFormat;
import com.behnam.insurancetest.exceptions.NonDatedEventException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

/**
 *  This class is a data structure to load event objects
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    EventType name;
    String contractId;
    Long premium;
    Long premiumReduction;
    Long premiumIncrease;
    String atDate;
    String startDate;
    String terminationDate;

    /**
     * To create a simple data structure used for calculation
     * @return
     */
    public ContractDetail getContractDetail() {
        return ContractDetail.builder()
                .contractId(contractId)
                .premium(premium!=null?premium:0)
                .build();
    }

    /**
     * Dates in an Event are not in the same field. this function returns filled date
     * @return String of date. priority: startDate, terminationDate, atDate
     * @throws if the events does not contain an event it will throw NonDatedEventException
     */
    public String getEventDate() {
        if (StringUtils.hasText(startDate)) {
            return startDate;
        } else if (StringUtils.hasText(terminationDate)) {
            return terminationDate;
        } else if (StringUtils.hasText(atDate)) {
            return atDate;
        } else {
            throw new NonDatedEventException("Event " + name + " on Contract " + contractId + " has not date");
        }
    }

    /**
     * extract month from Date String in format of YYYY-MM-DD or DD-MM-YYYY
     * @return month
     * @throws BadDateFormat if the format does not match
     * @throws NonDatedEventException if the event has not date
     */
    public Integer eventMonth() {
        String eventDate = getEventDate();
        try {
            return Integer.parseInt(eventDate.split("-")[1]);
        } catch (Exception e) {
            throw new BadDateFormat("Bad Date Format:" + eventDate);
        }
    }

}


