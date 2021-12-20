package com.aggregation.crm.web.client;

import com.aggregation.crm.collector.execption.HttpClientException;
import com.aggregation.crm.repository.model.CaseStatus;
import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.web.execption.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
public class RepositoryClientImpl implements RepositoryClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestTemplate restTemplate;

    @Value("${repository-server.filter_cases_url}")
    private String CASES_URL;

    public RepositoryClientImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<CrmCase> findByStatusAndProvider(String status, String provider, String errorCode) throws ParseException, HttpClientException {
        CaseStatus caseStatus = (status != null) ? CaseStatus.fromString(status) : null;
        String url = UriComponentsBuilder.fromHttpUrl(CASES_URL)
                .queryParam("status", caseStatus)
                .queryParam("providerName", provider)
                .queryParam("errorCode", errorCode)
                .toUriString();

        logger.info(String.format("about to get crm cases by making a GET call to %s", url));
        CrmCase[] responseArray;
        try {
            responseArray = restTemplate.getForEntity(url, CrmCase[].class).getBody();
        } catch (RestClientException e) {
            String errorMsg = String.format("Fail to execute GET call to %s", url);
            logger.error(errorMsg);
            throw new HttpClientException(errorMsg);
        }
        if (responseArray != null) {
            return Arrays.asList(responseArray);
        } else {
            return List.of();
        }
    }
}
