package com.aggregation.crm.repository.service;

import com.aggregation.crm.repository.exception.DaoException;
import com.aggregation.crm.repository.model.CaseStatus;
import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.repository.model.CrmProducer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Component
public interface CaseService {

    void save(List<? extends CrmCase> entities) throws DaoException;

    List<CrmCase> getByFilter(CaseStatus status, String providerName, String errorCode) throws DaoException;

    LocalDateTime getLastModifiedDateByCrmProducer(CrmProducer crmProducer) throws DaoException;
}
