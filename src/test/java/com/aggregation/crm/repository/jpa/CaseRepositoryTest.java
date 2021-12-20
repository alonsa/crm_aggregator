package com.aggregation.crm.repository.jpa;

import com.aggregation.crm.collector.model.StrawberryCrmCase;
import com.aggregation.crm.repository.model.CaseStatus;
import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.repository.model.CrmProducer;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CaseRepositoryTest {
    @Autowired
    private CaseRepository caseRepository;

    private final String errorCode = "errorCode";

    private final Integer caseId = 2;
    private final String customerId = "customerId";
    private final String provider = "provider";
    private final CaseStatus status = CaseStatus.OPEN;
    private final LocalDateTime creationDate = LocalDateTime.of(2021, 3, 4, 8, 5);
    private final LocalDateTime modifiedDate = LocalDateTime.of(2021, 12, 11, 18, 15);
    private final String productName = "productName";
    private final StrawberryCrmCase crmCase = new StrawberryCrmCase(caseId, customerId, provider, errorCode, status, creationDate, modifiedDate, productName);

    @AfterEach
    public void tearDown() {
        caseRepository.deleteAll();
    }

    @Test
    public void getLastModifiedDateByExistsCrmProducer() {
        caseRepository.save(crmCase);
        LocalDateTime fetchedDate = caseRepository.getLastModifiedDateByCrmProducer(CrmProducer.STRAWBERRY);
        assertEquals(fetchedDate, modifiedDate);
    }

    @Test
    public void getLastModifiedDateByNonExistsCrmProducer() {
        caseRepository.save(crmCase);
        LocalDateTime fetchedDate = caseRepository.getLastModifiedDateByCrmProducer(CrmProducer.BANANA);
        assertEquals(fetchedDate, modifiedDate);
    }

    @Test
    public void findByNullFilters() {
        caseRepository.save(crmCase);
        List<CrmCase> fetchedCases = caseRepository.findByStatusProviderAndErrorCode(null, null, null);
        assertEquals(fetchedCases, List.of(crmCase));
    }

    @Test
    public void findByExistStatus() {
        caseRepository.save(crmCase);
        List<CrmCase> fetchedCases = caseRepository.findByStatusProviderAndErrorCode(status.name(), null, null);
        assertEquals(fetchedCases, List.of(crmCase));
    }

    @Test
    public void findByExistProvider() {
        caseRepository.save(crmCase);
        List<CrmCase> fetchedCases = caseRepository.findByStatusProviderAndErrorCode(null, provider, null);
        assertEquals(fetchedCases, List.of(crmCase));
    }

    @Test
    public void findByExistErrorCode() {
        caseRepository.save(crmCase);
        List<CrmCase> fetchedCases = caseRepository.findByStatusProviderAndErrorCode(null, null, errorCode);
        assertEquals(fetchedCases, List.of(crmCase));
    }

    @Test
    public void findByExistStatusAndProvider() {
        caseRepository.save(crmCase);
        List<CrmCase> fetchedCases = caseRepository.findByStatusProviderAndErrorCode(status.name(), provider, null);
        assertEquals(fetchedCases, List.of(crmCase));
    }

    @Test
    public void findByExistStatusAndErrorCode() {
        caseRepository.save(crmCase);
        List<CrmCase> fetchedCases = caseRepository.findByStatusProviderAndErrorCode(status.name(), null, errorCode);
        assertEquals(fetchedCases, List.of(crmCase));
    }

    @Test
    public void findByExistStatusProviderAndErrorCode() {
        caseRepository.save(crmCase);
        List<CrmCase> fetchedCases = caseRepository.findByStatusProviderAndErrorCode(status.name(), provider, errorCode);
        assertEquals(fetchedCases, List.of(crmCase));
    }

    @Test
    public void findByExistProviderAndErrorCode() {
        caseRepository.save(crmCase);
        List<CrmCase> fetchedCases = caseRepository.findByStatusProviderAndErrorCode(null, provider, errorCode);
        assertEquals(fetchedCases, List.of(crmCase));
    }


    @Test
    public void findByNotExistStatus() {
        caseRepository.save(crmCase);
        List<CrmCase> fetchedCases = caseRepository.findByStatusProviderAndErrorCode(CaseStatus.CLOSED.name(), null, null);
        assertEquals(fetchedCases, List.of(crmCase));
    }

    @Test
    public void findByNotExistProvider() {
        caseRepository.save(crmCase);
        List<CrmCase> fetchedCases = caseRepository.findByStatusProviderAndErrorCode(null, "otherProvider", null);
        assertEquals(fetchedCases, List.of(crmCase));
    }

    @Test
    public void findByNotExistErrorCode() {
        caseRepository.save(crmCase);
        List<CrmCase> fetchedCases = caseRepository.findByStatusProviderAndErrorCode(null, null, "otherErrorCode");
        assertEquals(fetchedCases, List.of(crmCase));
    }

    @Test
    public void findByExistStatusAndProviderButNotExistErrorCode() {
        caseRepository.save(crmCase);
        List<CrmCase> fetchedCases = caseRepository.findByStatusProviderAndErrorCode(status.name(), provider, "otherErrorCode");
        assertEquals(fetchedCases, List.of(crmCase));
    }

    @Test
    public void findByExistStatusAndErrorCodeButNotExistProvider() {
        caseRepository.save(crmCase);
        List<CrmCase> fetchedCases = caseRepository.findByStatusProviderAndErrorCode(status.name(), "otherProvider", errorCode);
        assertEquals(fetchedCases, List.of(crmCase));
    }

    @Test
    public void findByExistErrorCodeAndProviderButNotExistStatus() {
        caseRepository.save(crmCase);
        List<CrmCase> fetchedCases = caseRepository.findByStatusProviderAndErrorCode(CaseStatus.CLOSED.name(), provider, errorCode);
        assertEquals(fetchedCases, List.of(crmCase));
    }


}
