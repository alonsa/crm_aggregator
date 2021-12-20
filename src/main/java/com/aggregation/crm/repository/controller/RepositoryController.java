package com.aggregation.crm.repository.controller;

import com.aggregation.crm.repository.model.AggregationExecution;
import com.aggregation.crm.repository.model.CaseStatus;
import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.repository.model.CrmProducer;
import com.aggregation.crm.repository.service.CaseService;
import com.aggregation.crm.repository.service.ExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/repository")
public class RepositoryController {

    @Autowired
    CaseService caseService;

    @Autowired
    ExecutionService executionService;

    @GetMapping("/case/filter")
    public ResponseEntity<List<CrmCase>> getCasesByFilter(@RequestParam(required = false) CaseStatus status, @RequestParam(required = false) String providerName, @RequestParam(required = false) String errorCode) {
        List<CrmCase> cases = caseService.getByFilter(status, providerName, errorCode);
        return new ResponseEntity<>(cases, HttpStatus.OK);
    }

    @GetMapping("/case/modified/last")
    public ResponseEntity<LocalDateTime> getLastModifiedDateByCrmProducer(@RequestParam() CrmProducer crmProducer) {
        LocalDateTime lastUpdatedDate = caseService.getLastModifiedDateByCrmProducer(crmProducer);
        return new ResponseEntity<>(lastUpdatedDate, HttpStatus.OK);
    }

    @PostMapping("/case/add/all")
    public ResponseEntity<CrmCase> addAll(@RequestBody List<? extends CrmCase> entity) {
        caseService.save(entity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/execution/last")
    public ResponseEntity<LocalDateTime> addAll(@RequestParam() CrmProducer crmProducer) {
        LocalDateTime lastExecutionDate = executionService.getLast(crmProducer);
        return new ResponseEntity<>(lastExecutionDate, HttpStatus.OK);
    }

    @PostMapping("/execution/add")
    public ResponseEntity<CrmCase> add(@RequestBody AggregationExecution entity) {
        executionService.update(entity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
