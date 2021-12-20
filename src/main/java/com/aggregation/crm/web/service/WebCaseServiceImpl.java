package com.aggregation.crm.web.service;

import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.web.client.RepositoryClient;
import com.aggregation.crm.web.model.ReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebCaseServiceImpl implements WebCaseService {

    @Autowired
    RepositoryClient repositoryClient;

    @Override
    public List<CrmCase> getByFilter(String status, String providerName, String errorCode) {
        return repositoryClient.findByStatusAndProvider(status, providerName, errorCode);
    }

    @Override
    public List<ReportResponse> getReportByFilter(String status, String providerName, String errorCode) {
        List<CrmCase> supportCases = repositoryClient.findByStatusAndProvider(status, providerName, errorCode);

        return supportCases.stream()
                .collect(Collectors.groupingBy(crmCase -> Pair.of(crmCase.getErrorCode(), crmCase.getProductName())))
                .entrySet()
                .stream().map(entry -> {
                    String caseErrorCode = entry.getKey().getFirst();
                    String caseProductName = entry.getKey().getSecond();
                    List<CrmCase> groupedSupportCases = entry.getValue();
                    return new ReportResponse(caseErrorCode, caseProductName, groupedSupportCases);
        }).collect(Collectors.toList());
    }

}
