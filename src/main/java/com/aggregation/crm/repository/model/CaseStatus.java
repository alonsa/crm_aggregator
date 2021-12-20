package com.aggregation.crm.repository.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.ValidationException;

public enum CaseStatus {
    @JsonProperty("open")
    @JsonAlias("Open")
    OPEN,
    @JsonProperty("Closed")
    @JsonAlias("closed")
    CLOSED;

    public static CaseStatus fromString(String str) {
        for (CaseStatus status: CaseStatus.values()){
            if (status.name().equals(str.toUpperCase())){
                return status;
            }
        }
        throw new ValidationException("No Enum with name " + str);
    }
}
