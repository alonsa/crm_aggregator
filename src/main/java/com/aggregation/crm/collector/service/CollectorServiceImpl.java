package com.aggregation.crm.collector.service;

import com.aggregation.crm.collector.client.RepositoryClient;
import com.aggregation.crm.collector.execption.HttpClientException;
import com.aggregation.crm.collector.execption.InvalidResponseException;
import com.aggregation.crm.collector.model.ListCases;
import com.aggregation.crm.repository.model.AggregationExecution;
import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.repository.model.CrmProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CollectorServiceImpl<E extends ListCases<? extends CrmCase>> implements CollectorService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RepositoryClient repositoryClient;

    @Value("${demand.aggregation.refresh.min.rate}")
    private Integer minRate;

    private final RestTemplate restTemplate;

    public CollectorServiceImpl(RepositoryClient repositoryClient, RestTemplateBuilder restTemplateBuilder) {
        this.repositoryClient = repositoryClient;
        this.restTemplate = restTemplateBuilder.build();
    }

    protected abstract String getServiceUrl();

    protected abstract Class<E> getEntityClass();

    protected abstract CrmProducer getCrmProducer();

    @Override
    public void collect() throws InvalidResponseException, HttpClientException {

        logger.info("starting to collect crm cases");
        List<? extends CrmCase> entities = getEntitiesFromCrmProducer();
        logger.info(String.format("got %d crm cases from remote crm system", entities.size()));

        logger.info(String.format("about to get the last case modified date from repository microservice by the crm producer [%s]", getCrmProducer()));
        LocalDateTime lastModifiedDate = repositoryClient.getLastCaseModifiedDate(getCrmProducer());
        logger.info(String.format("last case modified date of crm producer [%s] is: %s", getCrmProducer(), lastModifiedDate));

        logger.info("about to filter crm cases by last modified date");
        List<? extends CrmCase> filteredEntities = filterNewCasesByLastModifiedDate(entities, lastModifiedDate);
        logger.info(String.format("got %d crm cases after filtering", filteredEntities.size()));

        if (!filteredEntities.isEmpty()) {
            logger.info("about to save crm cases by repository microservice");
            repositoryClient.saveCases(filteredEntities);
        } else {
            logger.info("No new crm cases to save");
        }
        repositoryClient.saveExecution(new AggregationExecution(getCrmProducer()));
    }

    @Override
    public void collectByDemand() throws InvalidResponseException, HttpClientException {
        logger.info("starting to collect crm cases by user request");
        logger.info(String.format("about to get the last execution date from repository microservice by the crm producer [%s]", getCrmProducer()));
        LocalDateTime lastExecution = repositoryClient.getLastExecutionDate(getCrmProducer());
        logger.info(String.format("last execution date of crm producer [%s] is: %s", getCrmProducer(), lastExecution));

        if (lastExecution == null || lastExecution.isBefore(LocalDateTime.now().minusMinutes(minRate))) {
            collect();
        } else {
            logger.info(String.format("Crm collector run already in the past %d minutes. no need to perform another run", minRate));
        }
    }

    /*
    Since the crm APIs don't include a way to filter the responses, there is a need to filter the responded entities
     */
    private List<? extends CrmCase> filterNewCasesByLastModifiedDate(List<? extends CrmCase> entities, LocalDateTime lastModifiedDate) {
        return entities
                .stream()
                .filter(crmCase -> lastModifiedDate == null || crmCase.getModifiedDate().isAfter(lastModifiedDate))
                .collect(Collectors.toList());
    }

    private List<? extends CrmCase> getEntitiesFromCrmProducer() throws InvalidResponseException, HttpClientException {
        E entity;
        try {
            logger.info(String.format("about to get entities from crm producer by making a GET call to %s", getServiceUrl()));
            ResponseEntity<E> response = restTemplate.getForEntity(getServiceUrl(), getEntityClass());
            entity = response.getBody();
        } catch (Exception e) {
            String errorMsg = String.format("Fail to execute GET call to %s", getServiceUrl());
            logger.error(errorMsg);
            throw new HttpClientException(errorMsg);
        }

        if (entity == null) {
            String errorMsg = "No body in response from crm producer";
            logger.error(errorMsg);
            throw new InvalidResponseException(errorMsg);
        }

        return entity.getDate();
    }
}
