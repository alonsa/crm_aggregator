package com.aggregation.crm.web.service;

import com.aggregation.crm.collector.model.StrawberryCrmCase;
import com.aggregation.crm.repository.model.CaseStatus;
import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.web.client.RepositoryClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class WebCaseServiceTest {

    @Autowired
    private WebCaseService webCaseService;

    @MockBean
    private RepositoryClient repositoryClient;

    @Test
    public void performProvision_shouldPass() {
        String providerName = "providerName";
        String errorCode = "errorCode";

        Integer caseId = 2;
        String customerId = "customerId";
        String provider = "provider";
        CaseStatus status = CaseStatus.OPEN;
        LocalDateTime creationDate = LocalDateTime.of(2021, 3, 4, 8, 5);
        LocalDateTime modifiedDate = LocalDateTime.of(2021, 12, 11, 18, 15);
        String productName = "productName";
        StrawberryCrmCase crmCase = new StrawberryCrmCase(caseId, customerId, provider, errorCode, status, creationDate, modifiedDate, productName);

//        when(repositoryClient.findByStatusAndProvider(status.name(), providerName, errorCode)).thenReturn(List.of(crmCase));
//
//        List<CrmCase> result = webCaseService.getByFilter(status.name(), providerName, errorCode);
//        assertEquals(/result, List.of(crmCase));

    }
}
