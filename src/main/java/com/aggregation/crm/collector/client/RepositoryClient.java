package com.aggregation.crm.collector.client;

import com.aggregation.crm.repository.model.AggregationExecution;
import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.repository.model.CrmProducer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public interface RepositoryClient {

    LocalDateTime getLastCaseModifiedDate(CrmProducer crmProducer);

    void saveCases(List<? extends CrmCase> entities);

    void saveExecution(AggregationExecution entity);

    LocalDateTime getLastExecutionDate(CrmProducer crmProducer);

}
