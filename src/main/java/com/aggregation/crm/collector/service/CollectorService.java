package com.aggregation.crm.collector.service;

import com.aggregation.crm.collector.execption.HttpClientException;
import com.aggregation.crm.collector.execption.InvalidResponseException;
import org.springframework.stereotype.Component;

@Component
public interface CollectorService {
    void collect() throws InvalidResponseException, HttpClientException;
    void collectByDemand() throws InvalidResponseException, HttpClientException;
}
