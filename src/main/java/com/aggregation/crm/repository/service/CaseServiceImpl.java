package com.aggregation.crm.repository.service;

import com.aggregation.crm.repository.jpa.CaseRepository;
import com.aggregation.crm.repository.model.CaseStatus;
import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.repository.model.CrmProducer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CaseServiceImpl implements CaseService {

    private CaseRepository caseRepository;

    CaseServiceImpl(CaseRepository caseRepository){
        this.caseRepository = caseRepository;
    }

    @Override
    public void save(List<? extends CrmCase> entities){
        caseRepository.saveAll(entities);
    }

    @Override
    public List<CrmCase> getByFilter(CaseStatus status, String providerName, String errorCode) {
        String statusStr = status == null ? null : status.toString();
        return caseRepository.findByStatusProviderAndErrorCode(statusStr, providerName, errorCode);
    }

    @Override
    public LocalDateTime getLastModifiedDateByCrmProducer(CrmProducer crmProducer){
        return caseRepository.getLastModifiedDateByCrmProducer(crmProducer);
    }

}
