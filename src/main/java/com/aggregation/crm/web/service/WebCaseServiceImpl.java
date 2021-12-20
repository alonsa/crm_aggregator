package com.aggregation.crm.web.service;

import com.aggregation.crm.collector.execption.HttpClientException;
import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.web.client.RepositoryClient;
import com.aggregation.crm.web.execption.ParseException;
import com.aggregation.crm.web.model.ReportResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebCaseServiceImpl implements WebCaseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RepositoryClient repositoryClient;

    public WebCaseServiceImpl(RepositoryClient repositoryClient) {
        this.repositoryClient = repositoryClient;
    }

    @Override
    public List<ReportResponse> getReportByFilter(String status, String providerName, String errorCode) throws HttpClientException, ParseException {
        logger.info(String.format("get a crm cases with filters: status=[%s], providerName=[%s] and errorCode=[%s]", status, providerName, errorCode));
        List<CrmCase> supportCases = repositoryClient.findByStatusAndProvider(status, providerName, errorCode);

        return supportCases.stream()
                .collect(Collectors.groupingBy(crmCase -> Pair.of(crmCase.getErrorCode(), crmCase.getProvider())))
                .entrySet()
                .stream().map(entry -> {
                    String caseErrorCode = entry.getKey().getFirst();
                    String caseProvider = entry.getKey().getSecond();
                    List<CrmCase> groupedSupportCases = entry.getValue();
                    return new ReportResponse(caseProvider, caseErrorCode, groupedSupportCases);
        }).collect(Collectors.toList());
    }

}
