package com.aggregation.crm.collector.model;

import com.aggregation.crm.repository.model.CaseStatus;
import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.repository.model.CrmProducer;

import java.time.LocalDateTime;

public class BananaCrmCase extends CrmCase {

    public BananaCrmCase() {
        setCrmProducer(CrmProducer.BANANA);
    }

    public BananaCrmCase(Integer caseId, String customerId, String provider, String errorCode, CaseStatus status, LocalDateTime creationDate, LocalDateTime modifiedDate, String productName) {
        super(caseId, CrmProducer.BANANA, customerId, provider, errorCode, status, creationDate, modifiedDate, productName);
    }
}
