package com.aggregation.crm.web.client;

import com.aggregation.crm.collector.execption.HttpClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class CollectorClientImpl implements CollectorClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestTemplate restTemplate;

    @Value("${collector-server.execute_url}")
    private String executeUrl;

    public CollectorClientImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public void executeAggregation() throws HttpClientException {

        logger.info(String.format("about to execute aggregation by making a PUT call to %s", executeUrl));

        try {
            restTemplate.put(executeUrl, null);
        } catch (RestClientException e) {
            String errorMsg = String.format("Fail to execute GET call to %s", executeUrl);
            logger.error(errorMsg);
            throw new HttpClientException(errorMsg);
        }
    }

}
