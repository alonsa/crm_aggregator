package com.aggregation.crm.repository.service;

import com.aggregation.crm.repository.exception.DaoException;
import com.aggregation.crm.repository.jpa.CaseRepository;
import com.aggregation.crm.repository.model.CaseStatus;
import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.repository.model.CrmProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CaseServiceImpl implements CaseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CaseRepository caseRepository;

    CaseServiceImpl(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    @Override
    public void save(List<? extends CrmCase> entities) throws DaoException {
        logger.info(String.format("about to save %d cases", entities.size()));
        try {
            caseRepository.saveAll(entities);
        } catch (Exception e) {
            String errorMsg = String.format("Fail to save %d crm cases", entities.size());
            logger.error(errorMsg);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public List<CrmCase> getByFilter(CaseStatus status, String providerName, String errorCode) throws DaoException {
        String statusStr = status == null ? null : status.toString();
        logger.info(String.format("about get crm cases by filters status=[%s], providerName=[%s] and errorCode=[%s]", status, providerName, errorCode));
        try {
            return caseRepository.findByStatusProviderAndErrorCode(statusStr, providerName, errorCode);
        } catch (Exception e) {
            String errorMsg = "Fail to get crm cases";
            logger.error(errorMsg);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public LocalDateTime getLastModifiedDateByCrmProducer(CrmProducer crmProducer) throws DaoException {
        logger.info(String.format("about to get last modified date by crm provider %s", crmProducer));
        try {
            return caseRepository.getLastModifiedDateByCrmProducer(crmProducer);
        } catch (Exception e) {
            String errorMsg = "Fail to get last modified date";
            logger.error(errorMsg);
            throw new DaoException(errorMsg, e);
        }
    }

}
