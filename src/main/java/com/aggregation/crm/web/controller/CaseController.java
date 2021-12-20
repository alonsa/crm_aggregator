package com.aggregation.crm.web.controller;

import com.aggregation.crm.repository.model.CaseStatus;
import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.web.client.CollectorClient;
import com.aggregation.crm.web.model.ReportResponse;
import com.aggregation.crm.web.service.WebCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/case")
public class CaseController {

    @Autowired
    WebCaseService webCaseService;

    @Autowired
    CollectorClient collectorClient;

    @GetMapping("/report")
    public ResponseEntity<List<ReportResponse>> getReportByFilter(@RequestParam(required = false) String status, @RequestParam(required = false) String providerName, @RequestParam(required = false) String errorCode) {
        CaseStatus.fromString(status); // TODO add try
        List<ReportResponse> report = webCaseService.getReportByFilter(status, providerName, errorCode);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @PutMapping("/aggregate/all")
    public ResponseEntity<CrmCase> executeAggregation() {
        collectorClient.executeAggregation();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
