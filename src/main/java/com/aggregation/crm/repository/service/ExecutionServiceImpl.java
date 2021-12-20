package com.aggregation.crm.repository.service;

import com.aggregation.crm.repository.exception.DaoException;
import com.aggregation.crm.repository.jpa.ExecutionRepository;
import com.aggregation.crm.repository.model.AggregationExecution;
import com.aggregation.crm.repository.model.CrmProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ExecutionServiceImpl implements ExecutionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ExecutionRepository executionRepository;

    public ExecutionServiceImpl(ExecutionRepository executionRepository) {
        this.executionRepository = executionRepository;
    }

    @Override
    public LocalDateTime getLastDateByCrmProducer(CrmProducer crmProducer) throws DaoException {
        logger.info(String.format("about to get last modified date by crm provider %s", crmProducer));
        try {
            return executionRepository.getLastExecutionDate(crmProducer);
        } catch (Exception e) {
            String errorMsg = "Fail to get last modified date";
            logger.error(errorMsg);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public void save(AggregationExecution entity) throws DaoException {

        logger.info("about to save an execution");
        try {
            executionRepository.save(entity);
        } catch (Exception e) {
            String errorMsg = "Fail to save execution";
            logger.error(errorMsg);
            throw new DaoException(errorMsg, e);
        }
    }
}
