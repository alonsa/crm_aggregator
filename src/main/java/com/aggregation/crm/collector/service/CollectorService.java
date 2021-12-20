package com.aggregation.crm.collector.service;

import org.springframework.stereotype.Component;

@Component
public interface CollectorService {
    void collect();
    void collectByDemand();
}
