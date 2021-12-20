package com.aggregation.crm.collector.service;

import com.aggregation.crm.collector.client.RepositoryClient;
import com.aggregation.crm.collector.execption.HttpClientException;
import com.aggregation.crm.collector.execption.InvalidResponseException;
import com.aggregation.crm.collector.model.BananaListCases;
import com.aggregation.crm.repository.model.CrmProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class BananaCollectorServiceImpl extends CollectorServiceImpl<BananaListCases> implements BananaCollectorService {

    private final Logger logger = LoggerFactory.getLogger(BananaCollectorServiceImpl.class);

    @Value("${services.banana.url: http://localhost:8083/crm/banana}")
    private String url;

    public BananaCollectorServiceImpl(RepositoryClient repositoryClient, RestTemplateBuilder restTemplateBuilder) {
        super(repositoryClient, restTemplateBuilder);
    }

    @Override
    protected String getServiceUrl() {
        return url;
    }

    @Override
    protected Class<BananaListCases> getEntityClass() {
        return BananaListCases.class;
    }

    @Override
    protected CrmProducer getCrmProducer() {
        return CrmProducer.BANANA;
    }

    @Override
    @Scheduled(fixedRateString = "${services.banana.rate: PT4H}")
    public void collect() throws HttpClientException, InvalidResponseException {
        logger.info("scheduled job invoked. about to collect crm cases");
        super.collect();
    }

}
