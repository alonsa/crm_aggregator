package com.aggregation.crm.repository.controller;

import com.aggregation.crm.repository.exception.DaoException;
import com.aggregation.crm.repository.model.AggregationExecution;
import com.aggregation.crm.repository.model.CaseStatus;
import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.repository.model.CrmProducer;
import com.aggregation.crm.repository.service.CaseService;
import com.aggregation.crm.repository.service.ExecutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/repository")
public class RepositoryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CaseService caseService;

    private final ExecutionService executionService;

    public RepositoryController(CaseService caseService, ExecutionService executionService) {
        this.caseService = caseService;
        this.executionService = executionService;
    }

    @GetMapping("/case/filter")
    public ResponseEntity<List<CrmCase>> getCasesByFilter(@RequestParam(required = false) CaseStatus status, @RequestParam(required = false) String providerName, @RequestParam(required = false) String errorCode) throws DaoException {
        logger.info(String.format("get crm cases with filter request by status=[%s], providerName=[%s] and errorCode=[%s]", status, providerName, errorCode));
        List<CrmCase> cases = caseService.getByFilter(status, providerName, errorCode);
        logger.info(String.format("return %s crm cases", cases.size()));
        return new ResponseEntity<>(cases, HttpStatus.OK);
    }

    @GetMapping("/case/modified/last")
    public ResponseEntity<LocalDateTime> getLastModifiedDateByCrmProducer(@RequestParam() CrmProducer crmProducer) throws DaoException {
        logger.info(String.format("get last update date by crm provider [%s] request", crmProducer));
        LocalDateTime lastUpdatedDate = caseService.getLastModifiedDateByCrmProducer(crmProducer);
        logger.info(String.format("case update date is: %s", lastUpdatedDate));
        return new ResponseEntity<>(lastUpdatedDate, HttpStatus.OK);
    }

    @PostMapping("/case/add/all")
    public ResponseEntity<CrmCase> addAll(@RequestBody List<? extends CrmCase> cases) throws DaoException {
        logger.info(String.format("get add all crm cases request with [%d] cases", cases.size()));
        caseService.save(cases);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/execution/last")
    public ResponseEntity<LocalDateTime> addAll(@RequestParam() CrmProducer crmProducer) throws DaoException {
        logger.info(String.format("get execution last date by crm provider [%s] request", crmProducer));
        LocalDateTime lastExecutionDate = executionService.getLastDateByCrmProducer(crmProducer);
        logger.info(String.format("execution update date is: %s", lastExecutionDate));
        return new ResponseEntity<>(lastExecutionDate, HttpStatus.OK);
    }

    @PostMapping("/execution/add")
    public ResponseEntity<CrmCase> add(@RequestBody AggregationExecution entity) throws DaoException {
        executionService.save(entity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
