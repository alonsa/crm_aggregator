package com.aggregation.crm.web.client;

import com.aggregation.crm.collector.execption.HttpClientException;
import org.springframework.stereotype.Component;

@Component
public interface CollectorClient {

    void executeAggregation() throws HttpClientException;

}
