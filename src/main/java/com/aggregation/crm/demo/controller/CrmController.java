package com.aggregation.crm.demo.controller;

import com.aggregation.crm.collector.model.ListCases;
import com.aggregation.crm.repository.model.CaseStatus;
import com.aggregation.crm.repository.model.CrmCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/crm")
public class CrmController {

    private final Random random = new Random();

    @GetMapping("/strawberry")
    public ResponseEntity<ListCases<? extends CrmCase>> getStrawberryCases() {
        ListCases<? extends CrmCase> cases = getCrmCases();
        return new ResponseEntity<>(cases, HttpStatus.OK);
    }

    @GetMapping("/banana")
    public ResponseEntity<ListCases<? extends CrmCase>> getBananaCases() {
        ListCases<? extends CrmCase> cases = getCrmCases();
        return new ResponseEntity<>(cases, HttpStatus.OK);
    }

    private ListCases<? extends CrmCase> getCrmCases() {
        List<CrmCase> cases = IntStream.range(0,10)

                .mapToObj(i -> new CrmCase(
                        i,
                        null,
                        "customerId_" + random.nextInt(50),
                        "provider_" + random.nextInt(50),
                        "errorCode_" + random.nextInt(50),
                        random.nextBoolean() ? CaseStatus.CLOSED : CaseStatus.OPEN,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        "productName")
                ).collect(Collectors.toList());
        return new ListCases<>(cases);
    }
}
