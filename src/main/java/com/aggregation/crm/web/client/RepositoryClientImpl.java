package com.aggregation.crm.web.client;

import com.aggregation.crm.repository.model.CaseStatus;
import com.aggregation.crm.repository.model.CrmCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RepositoryClientImpl implements RepositoryClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${repository-server.filter_cases_url}")
    private String CASES_URL;

    @Override
    public List<CrmCase> findByStatusAndProvider(String status, String provider, String errorCode) {
            String lastUpdateUrl = UriComponentsBuilder.fromHttpUrl(CASES_URL)
                    .queryParam("status", Optional.ofNullable(status).map(CaseStatus::fromString))
                    .queryParam("providerName", provider)
                    .queryParam("errorCode", errorCode)
                    .toUriString();

        CrmCase[] responseArray = restTemplate.getForEntity(lastUpdateUrl, CrmCase[].class).getBody();
        if (responseArray != null) {
            return Arrays.asList(responseArray);
        }else{
            throw new IllegalStateException("No body in response");
        }
    }
}
