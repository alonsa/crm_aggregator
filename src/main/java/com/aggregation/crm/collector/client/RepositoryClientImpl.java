package com.aggregation.crm.collector.client;

import com.aggregation.crm.repository.model.AggregationExecution;
import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.repository.model.CrmProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RepositoryClientImpl implements RepositoryClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${repository-server.save_url}")
    private String saveAllUrl;

    @Value("${repository-server.save_execution_url}")
    private String saveExecutionUrl;

    @Value("${repository-server.last_update_url}")
    private String lastUpdateUrl;


    @Value("${repository-server.last_execution_url}")
    private String lastExecutionUrl;

    @Override
    public LocalDateTime getLastCaseModifiedDate(CrmProducer crmProducer) {
        String lastUpdateUrl = UriComponentsBuilder.fromHttpUrl(this.lastUpdateUrl).queryParam("crmProducer", crmProducer).toUriString();
        return restTemplate.getForEntity(lastUpdateUrl, LocalDateTime.class).getBody();
    }

    @Override
    public void saveCases(List<? extends CrmCase> entities) {
        restTemplate.postForEntity(saveAllUrl, entities, Void.class);
    }

    @Override
    public void saveExecution(AggregationExecution entity) {
        restTemplate.postForEntity(saveExecutionUrl, entity, Void.class);
    }

    @Override
    public LocalDateTime getLastExecutionDate(CrmProducer crmProducer) {
        String lastExecutionUrl = UriComponentsBuilder.fromHttpUrl(this.lastExecutionUrl).queryParam("CrmProducer", crmProducer).toUriString();
        return restTemplate.getForEntity(lastExecutionUrl, LocalDateTime.class).getBody();
    }
}
