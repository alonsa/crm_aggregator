package com.aggregation.crm.web.service;

import com.aggregation.crm.collector.execption.HttpClientException;
import com.aggregation.crm.collector.model.StrawberryCrmCase;
import com.aggregation.crm.repository.model.CaseStatus;
import com.aggregation.crm.web.client.RepositoryClient;
import com.aggregation.crm.web.execption.ParseException;
import com.aggregation.crm.web.model.ReportResponse;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebCaseServiceTest {

    private final RepositoryClient mockRepositoryClient = mock(RepositoryClient.class);
    private final WebCaseService webCaseService = new WebCaseServiceImpl(mockRepositoryClient);

    @Test
    public void successGettingReportByFilter() throws HttpClientException, ParseException {
        String errorCode = "errorCode";

        Integer caseId = 2;
        String customerId = "customerId";
        String provider = "provider";
        CaseStatus status = CaseStatus.OPEN;
        LocalDateTime creationDate = LocalDateTime.of(2021, 3, 4, 8, 5);
        LocalDateTime modifiedDate = LocalDateTime.of(2021, 12, 11, 18, 15);
        String productName = "productName";
        StrawberryCrmCase crmCase = new StrawberryCrmCase(caseId, customerId, provider, errorCode, status, creationDate, modifiedDate, productName);

        when(mockRepositoryClient.findByStatusAndProvider(status.name(), provider, errorCode)).thenReturn(List.of(crmCase));

        List<ReportResponse> result = webCaseService.getReportByFilter(status.name(), provider, errorCode);

        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getErrorCode(), errorCode);
        assertEquals(result.get(0).getProvider(), provider);
        assertEquals(result.get(0).getSupportCases(), List.of(crmCase));
        assertEquals(result.get(0).getProductsEffected(), Set.of(productName));
        assertEquals(result.get(0).getSupportCaseNumber().longValue(), 1);
    }

    @Test
    public void successGettingReportByFilterAndGroup() throws HttpClientException, ParseException {
        String providerName = "providerName";
        String provider = "provider";
        CaseStatus status = CaseStatus.OPEN;
        String errorCode = "errorCode";

        Integer caseId1 = 1;
        String customerId1 = "customerId1";
        LocalDateTime creationDate1 = LocalDateTime.of(2021, 3, 4, 8, 5);
        LocalDateTime modifiedDate1 = LocalDateTime.of(2021, 12, 11, 18, 15);
        String productName1 = "productName1";

        Integer caseId2 = 2;
        String customerId2 = "customerId2";
        LocalDateTime creationDate2 = LocalDateTime.of(2021, 3, 4, 8, 5);
        LocalDateTime modifiedDate2 = LocalDateTime.of(2021, 12, 11, 18, 15);
        String productName2 = "productName2";
        StrawberryCrmCase crmCase1 = new StrawberryCrmCase(caseId1, customerId1, provider, errorCode, status, creationDate1, modifiedDate1, productName1);
        StrawberryCrmCase crmCase2 = new StrawberryCrmCase(caseId2, customerId2, provider, errorCode, status, creationDate2, modifiedDate2, productName2);

        when(mockRepositoryClient.findByStatusAndProvider(status.name(), providerName, errorCode)).thenReturn(List.of(crmCase1, crmCase2));

        List<ReportResponse> result = webCaseService.getReportByFilter(status.name(), providerName, errorCode);

        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getErrorCode(), errorCode);
        assertEquals(result.get(0).getProvider(), provider);
        assertEquals(result.get(0).getSupportCases(), List.of(crmCase1, crmCase2));
        assertEquals(result.get(0).getProductsEffected(), Set.of(productName1, productName2));
        assertEquals(result.get(0).getSupportCaseNumber().longValue(), 2);
    }
}
