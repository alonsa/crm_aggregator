package com.aggregation.crm.repository.service;

import com.aggregation.crm.repository.model.AggregationExecution;
import com.aggregation.crm.repository.jpa.ExecutionRepository;
import com.aggregation.crm.repository.model.CrmProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ExecutionServiceImpl implements ExecutionService {

    @Autowired
    ExecutionRepository executionRepository;

    @Override
    public LocalDateTime getLast(CrmProducer crmProducer) {
        return executionRepository.getLastExecutionDate(crmProducer);
    }

    @Override
    public void update(AggregationExecution entity) {
        executionRepository.save(entity);
    }
}
