package com.aggregation.crm.web.controller;

import com.aggregation.crm.collector.execption.HttpClientException;
import com.aggregation.crm.repository.model.CaseStatus;
import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.web.client.CollectorClient;
import com.aggregation.crm.web.execption.ParseException;
import com.aggregation.crm.web.model.ReportResponse;
import com.aggregation.crm.web.service.WebCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/case")
public class CaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WebCaseService webCaseService;

    private final CollectorClient collectorClient;

    public CaseController(WebCaseService webCaseService, CollectorClient collectorClient) {
        this.webCaseService = webCaseService;
        this.collectorClient = collectorClient;
    }

    @GetMapping("/report")
    public ResponseEntity<List<ReportResponse>> getReportByFilter(@RequestParam(required = false) String status, @RequestParam(required = false) String providerName, @RequestParam(required = false) String errorCode) throws ParseException, HttpClientException {
        if (status != null) {
            logger.info(String.format("validation status requestParam [%s] is a valid caseStatus", status));
            CaseStatus.fromString(status);
        }

        logger.info(String.format("get a report with filter request by status=[%s], providerName=[%s] and errorCode=[%s]", status, providerName, errorCode));
        List<ReportResponse> report = webCaseService.getReportByFilter(status, providerName, errorCode);
        logger.info(String.format("return a report [%s]", report));
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @PutMapping("/aggregate/all")
    public ResponseEntity<CrmCase> executeAggregation() throws HttpClientException {
        logger.info("get an on demand aggregation request");
        collectorClient.executeAggregation();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
