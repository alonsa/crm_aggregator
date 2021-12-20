package com.aggregation.crm.collector.service;

import com.aggregation.crm.collector.client.RepositoryClient;
import com.aggregation.crm.collector.model.ListCases;
import com.aggregation.crm.repository.model.AggregationExecution;
import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.repository.model.CrmProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CollectorServiceImpl<E extends ListCases<? extends CrmCase>> implements CollectorService {

    Logger logger = LoggerFactory.getLogger(CollectorServiceImpl.class);

//    @Autowired
    private RepositoryClient repositoryClient;


    public CollectorServiceImpl(RepositoryClient repositoryClient) {
        this.repositoryClient = repositoryClient;
    }

    private final RestTemplate restTemplate = new RestTemplate();

    protected abstract String getServiceUrl();

    protected abstract Class<E> getEntityClass();

    protected abstract CrmProducer getCrmProducer();

    @Override
    public void collect() {

        List<? extends CrmCase> entities = getEntitiesFromCrmProducer();

        LocalDateTime lastModifiedDate = repositoryClient.getLastCaseModifiedDate(getCrmProducer());
        List<? extends CrmCase> filteredEntities = filterNewCasesByLastModifiedDate(entities, lastModifiedDate);
        if (!filteredEntities.isEmpty()) {
            repositoryClient.saveCases(filteredEntities);
        } else {
            // TODO log
        }
        repositoryClient.saveExecution(new AggregationExecution(getCrmProducer()));
    }

    @Override
    public void collectByDemand() {
        LocalDateTime lastExecution = repositoryClient.getLastExecutionDate(getCrmProducer());
        if (lastExecution.isBefore(LocalDateTime.now().minusMinutes(15))) { // TODO set 15 to config
            collect();
        } else {
            // TODO log
        }
    }

    private List<? extends CrmCase> filterNewCasesByLastModifiedDate(List<? extends CrmCase> entities, LocalDateTime lastModifiedDate) {
        return entities
                .stream()
                .filter(crmCase -> lastModifiedDate == null || crmCase.getModifiedDate().isAfter(lastModifiedDate))
                .collect(Collectors.toList());
    }

    private List<? extends CrmCase> getEntitiesFromCrmProducer() {
        E entity = null;
        try {
            ResponseEntity<E> response = restTemplate.getForEntity(getServiceUrl(), getEntityClass());
            entity = response.getBody();
        } catch (Exception e) {
            // Since the fetch url is fake, we use sample entities
        }

        if (entity == null) {
            throw new IllegalStateException("No body in response");
        }

        return entity.getDate();
    }
}
