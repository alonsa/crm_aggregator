package com.aggregation.crm.repository.service;

import com.aggregation.crm.collector.model.StrawberryCrmCase;
import com.aggregation.crm.repository.exception.DaoException;
import com.aggregation.crm.repository.jpa.CaseRepository;
import com.aggregation.crm.repository.model.CaseStatus;
import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.repository.model.CrmProducer;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class CaseServiceTest {

    private final CaseRepository mockCaseRepository = mock(CaseRepository.class);
    private final CaseService webCaseService = new CaseServiceImpl(mockCaseRepository);

    @Test
    public void successSaveCrmCases() throws DaoException {
        String errorCode = "errorCode";
        Integer caseId = 2;
        String customerId = "customerId";
        String provider = "provider";
        CaseStatus status = CaseStatus.OPEN;
        LocalDateTime creationDate = LocalDateTime.of(2021, 3, 4, 8, 5);
        LocalDateTime modifiedDate = LocalDateTime.of(2021, 12, 11, 18, 15);
        String productName = "productName";
        StrawberryCrmCase crmCase = new StrawberryCrmCase(caseId, customerId, provider, errorCode, status, creationDate, modifiedDate, productName);
        webCaseService.save(List.of(crmCase));
        verify(mockCaseRepository).saveAll(List.of(crmCase));
    }

    @Test
    public void failSaveCrmCases() {
        String errorCode = "errorCode";
        Integer caseId = 2;
        String customerId = "customerId";
        String provider = "provider";
        CaseStatus status = CaseStatus.OPEN;
        LocalDateTime creationDate = LocalDateTime.of(2021, 3, 4, 8, 5);
        LocalDateTime modifiedDate = LocalDateTime.of(2021, 12, 11, 18, 15);
        String productName = "productName";
        StrawberryCrmCase crmCase = new StrawberryCrmCase(caseId, customerId, provider, errorCode, status, creationDate, modifiedDate, productName);
        when(mockCaseRepository.saveAll(List.of(crmCase))).thenThrow(new RuntimeException("test exception"));
        assertThrows(DaoException.class, () -> webCaseService.save(List.of(crmCase)));
    }

    @Test
    public void successGetByFilterWithCaseStatusFilter() throws DaoException {
        String errorCode = "errorCode";
        Integer caseId = 2;
        String customerId = "customerId";
        String provider = "provider";
        CaseStatus status = CaseStatus.OPEN;
        LocalDateTime creationDate = LocalDateTime.of(2021, 3, 4, 8, 5);
        LocalDateTime modifiedDate = LocalDateTime.of(2021, 12, 11, 18, 15);
        String productName = "productName";
        StrawberryCrmCase crmCase = new StrawberryCrmCase(caseId, customerId, provider, errorCode, status, creationDate, modifiedDate, productName);

        when(mockCaseRepository.findByStatusProviderAndErrorCode("OPEN", null, null)).thenReturn(List.of(crmCase));
        List<CrmCase> cases = webCaseService.getByFilter(CaseStatus.OPEN, null, null);

        assertEquals(cases, List.of(crmCase));
    }

    @Test
    public void successGetByFilterWithoutCaseStatusFilter() throws DaoException {
        String errorCode = "errorCode";
        Integer caseId = 2;
        String customerId = "customerId";
        String provider = "provider";
        CaseStatus status = CaseStatus.OPEN;
        LocalDateTime creationDate = LocalDateTime.of(2021, 3, 4, 8, 5);
        LocalDateTime modifiedDate = LocalDateTime.of(2021, 12, 11, 18, 15);
        String productName = "productName";
        StrawberryCrmCase crmCase = new StrawberryCrmCase(caseId, customerId, provider, errorCode, status, creationDate, modifiedDate, productName);

        when(mockCaseRepository.findByStatusProviderAndErrorCode(null, null, null)).thenReturn(List.of(crmCase));
        List<CrmCase> cases = webCaseService.getByFilter(null, null, null);

        assertEquals(cases, List.of(crmCase));
    }

    @Test
    public void failGetByFilter() {
        when(mockCaseRepository.findByStatusProviderAndErrorCode(null, null, null)).thenThrow(new RuntimeException("test exception"));
        assertThrows(DaoException.class, () -> webCaseService.getByFilter(null, null, null));
    }

    @Test
    public void successGetLastModifiedDateByCrmProducer() throws DaoException {
        LocalDateTime date = LocalDateTime.now();
        when(mockCaseRepository.getLastModifiedDateByCrmProducer(CrmProducer.BANANA)).thenReturn(date);
        LocalDateTime lastModifiedDate = webCaseService.getLastModifiedDateByCrmProducer(CrmProducer.BANANA);

        assertEquals(date, lastModifiedDate);
    }

    @Test
    public void failGetLastModifiedDateByCrmProducer() throws DaoException {
        when(mockCaseRepository.getLastModifiedDateByCrmProducer(CrmProducer.BANANA)).thenThrow(new RuntimeException("test exception"));
        assertThrows(DaoException.class, () -> webCaseService.getLastModifiedDateByCrmProducer(CrmProducer.BANANA));
    }

}
