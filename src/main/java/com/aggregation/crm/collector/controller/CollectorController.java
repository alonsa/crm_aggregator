package com.aggregation.crm.collector.controller;

import com.aggregation.crm.collector.execption.HttpClientException;
import com.aggregation.crm.collector.execption.InvalidResponseException;
import com.aggregation.crm.collector.service.BananaCollectorService;
import com.aggregation.crm.collector.service.CollectorService;
import com.aggregation.crm.collector.service.StrawberryCollectorService;
import com.aggregation.crm.repository.model.CrmCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/collector")
public class CollectorController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final List<CollectorService> services;

    public CollectorController(BananaCollectorService bananaCollectorService, StrawberryCollectorService strawberryCollectorService) {
        services = List.of(bananaCollectorService, strawberryCollectorService);
    }

    @PutMapping("/aggregate/all")
    public ResponseEntity<CrmCase> executeAggregation() throws HttpClientException, InvalidResponseException {
        logger.info("get an on demand aggregation request");

        for (CollectorService service: services){
            service.collectByDemand();
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
