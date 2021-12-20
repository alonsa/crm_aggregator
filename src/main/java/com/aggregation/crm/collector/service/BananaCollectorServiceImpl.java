package com.aggregation.crm.collector.service;

import com.aggregation.crm.collector.client.RepositoryClient;
import com.aggregation.crm.collector.model.BananaListCases;
import com.aggregation.crm.repository.model.CrmProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class BananaCollectorServiceImpl extends CollectorServiceImpl<BananaListCases> implements BananaCollectorService {

    @Value("${services.banana.url}")
    private String url;

    public BananaCollectorServiceImpl(RepositoryClient repositoryClient) {
        super(repositoryClient);
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
    public void collect() {
        super.collect();
    }

}
