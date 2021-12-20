package com.aggregation.crm.collector.service;

import com.aggregation.crm.collector.client.RepositoryClient;
import com.aggregation.crm.collector.model.StrawberryListCases;
import com.aggregation.crm.repository.model.CrmProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StrawberryCollectorServiceImpl extends CollectorServiceImpl<StrawberryListCases> implements StrawberryCollectorService {

    @Value("${services.strawberry.url}")
    private String url;

    public StrawberryCollectorServiceImpl(RepositoryClient repositoryClient) {
        super(repositoryClient);
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
    @Scheduled(fixedRateString = "${services.strawberry.rate: PT4S}")
    public void collect() {
        super.collect();
    }
}
