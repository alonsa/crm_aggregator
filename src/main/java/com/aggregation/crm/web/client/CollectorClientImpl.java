package com.aggregation.crm.web.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CollectorClientImpl implements CollectorClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${collector-server.execute_url}")
    private String executeUrl;

    @Override
    public void executeAggregation() {
        restTemplate.put(executeUrl, null);
    }

}
