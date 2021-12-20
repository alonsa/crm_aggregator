package com.aggregation.crm.repository.service;

import com.aggregation.crm.repository.model.AggregationExecution;
import com.aggregation.crm.repository.model.CrmProducer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public interface ExecutionService {
    LocalDateTime getLast(CrmProducer crmProducer);

    void update(AggregationExecution entity);
}
