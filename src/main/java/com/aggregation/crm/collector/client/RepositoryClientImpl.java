package com.aggregation.crm.collector.client;

import com.aggregation.crm.collector.execption.HttpClientException;
import com.aggregation.crm.repository.model.AggregationExecution;
import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.repository.model.CrmProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RepositoryClientImpl implements RepositoryClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestTemplate restTemplate;

    @Value("${repository-server.save_url}")
    private String saveAllUrl;

    @Value("${repository-server.save_execution_url}")
    private String saveExecutionUrl;

    @Value("${repository-server.last_update_url}")
    private String lastUpdateUrl;


    @Value("${repository-server.last_execution_url}")
    private String lastExecutionUrl;

    public RepositoryClientImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public LocalDateTime getLastCaseModifiedDate(CrmProducer crmProducer) throws HttpClientException {
        String url = UriComponentsBuilder.fromHttpUrl(this.lastUpdateUrl).queryParam("crmProducer", crmProducer).toUriString();
        logger.info(String.format("about to get last case modified date by making a GET call to %s", url));

        try {
            return restTemplate.getForEntity(url, LocalDateTime.class).getBody();
        } catch (RestClientException e) {
            String errorMsg = String.format("Fail to execute GET call to %s", url);
            logger.error(errorMsg);
            throw new HttpClientException(errorMsg);
        }
    }

    @Override
    public void saveCases(List<? extends CrmCase> entities) throws HttpClientException {

        logger.info(String.format("about to save %d crm cases by making a POST call to %s", entities.size(), saveAllUrl));

        try {
            restTemplate.postForEntity(saveAllUrl, entities, Void.class);
        } catch (RestClientException e) {
            String errorMsg = String.format("Fail to execute POST call to %s", saveAllUrl);
            logger.error(errorMsg);
            throw new HttpClientException(errorMsg);
        }
    }

    @Override
    public void saveExecution(AggregationExecution entity) throws HttpClientException {
        logger.info(String.format("about to save aggregation execution entity by making a POST call to %s", saveExecutionUrl));

        try {
            restTemplate.postForEntity(saveExecutionUrl, entity, Void.class);
        } catch (RestClientException e) {
            String errorMsg = String.format("Fail to execute POST call to %s", saveExecutionUrl);
            logger.error(errorMsg);
            throw new HttpClientException(errorMsg);
        }
    }

    @Override
    public LocalDateTime getLastExecutionDate(CrmProducer crmProducer) throws HttpClientException {
        String url = UriComponentsBuilder.fromHttpUrl(this.lastExecutionUrl).queryParam("crmProducer", crmProducer).toUriString();
        logger.info(String.format("about to get last execution date by making a GET call to %s", url));

        try {
            return restTemplate.getForEntity(url, LocalDateTime.class).getBody();
        } catch (RestClientException e) {
            String errorMsg = String.format("Fail to execute GET call to %s", url);
            logger.error(errorMsg);
            throw new HttpClientException(errorMsg);
        }
    }
}
