package com.aggregation.crm.repository.service;

import com.aggregation.crm.repository.exception.DaoException;
import com.aggregation.crm.repository.model.AggregationExecution;
import com.aggregation.crm.repository.model.CrmProducer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public interface ExecutionService {
    LocalDateTime getLastDateByCrmProducer(CrmProducer crmProducer) throws DaoException;

    void save(AggregationExecution entity) throws DaoException;
}
