package com.aggregation.crm.collector.service;

import com.aggregation.crm.collector.client.RepositoryClient;
import com.aggregation.crm.collector.execption.HttpClientException;
import com.aggregation.crm.collector.execption.InvalidResponseException;
import com.aggregation.crm.collector.model.StrawberryListCases;
import com.aggregation.crm.repository.model.CrmProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StrawberryCollectorServiceImpl extends CollectorServiceImpl<StrawberryListCases> implements StrawberryCollectorService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${services.strawberry.url}")
    private String url;

    public StrawberryCollectorServiceImpl(RepositoryClient repositoryClient, RestTemplateBuilder restTemplateBuilder) {
        super(repositoryClient, restTemplateBuilder);
    }

    @Override
    protected String getServiceUrl() {
        return url;
    }

    @Override
    protected Class<StrawberryListCases> getEntityClass() {
        return StrawberryListCases.class;
    }

    @Override
    protected CrmProducer getCrmProducer() {
        return CrmProducer.STRAWBERRY;
    }

    @Override
    @Scheduled(fixedRateString = "${services.strawberry.rate: PT4H}")
    public void collect() throws HttpClientException, InvalidResponseException {
        logger.info("scheduled job invoked. about to collect crm cases");
        super.collect();
    }
}
