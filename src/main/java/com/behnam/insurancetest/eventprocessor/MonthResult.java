package com.behnam.insurancetest.eventprocessor;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MonthResult {
    /**
     * Number of contracts in this month
     */
    long contractsCount;
    /**
     * The expected sum of all premiums for the year.
     */
    long egwp;
    /**
     * The accumulated premium that should have been paid in every month.
     */
    long agwp;
}
