package com.aggregation.crm.repository.model;

import com.aggregation.crm.web.execption.ParseException;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum CaseStatus {
    @JsonProperty("open")
    @JsonAlias("Open")
    OPEN,
    @JsonProperty("Closed")
    @JsonAlias("closed")
    CLOSED;

    public static CaseStatus fromString(String str) throws ParseException {
        for (CaseStatus status: CaseStatus.values()){
            if (status.name().equals(str.toUpperCase())){
                return status;
            }
        }
        throw new ParseException(String.format("For CaseStatus Enum, No match for str %s", str));
    }
}
