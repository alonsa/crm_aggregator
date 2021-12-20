package com.aggregation.crm.collector.controller;

import com.aggregation.crm.collector.service.BananaCollectorService;
import com.aggregation.crm.collector.service.StrawberryCollectorService;
import com.aggregation.crm.repository.model.CrmCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collector")
public class CollectorController {

    @Autowired
    BananaCollectorService bananaCollectorService;

    @Autowired
    StrawberryCollectorService strawberryCollectorService;

    @PutMapping("/aggregate/all")
    public ResponseEntity<CrmCase> executeAggregation() {
        bananaCollectorService.collectByDemand();
        strawberryCollectorService.collectByDemand();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
