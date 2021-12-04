package com.behnam.insurancetest.eventprocessor;

import lombok.Builder;
import lombok.Data;

/**
 * A simple data structure to save Contract Data
 */
@Data
@Builder
public class ContractDetail {
    String contractId;
    long premium;
}
