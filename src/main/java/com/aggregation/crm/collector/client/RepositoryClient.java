package com.aggregation.crm.collector.client;

import com.aggregation.crm.collector.execption.HttpClientException;
import com.aggregation.crm.repository.model.AggregationExecution;
import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.repository.model.CrmProducer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public interface RepositoryClient {

    LocalDateTime getLastCaseModifiedDate(CrmProducer crmProducer) throws HttpClientException;

    void saveCases(List<? extends CrmCase> entities) throws HttpClientException;

    void saveExecution(AggregationExecution entity) throws HttpClientException;

    LocalDateTime getLastExecutionDate(CrmProducer crmProducer) throws HttpClientException;

}
