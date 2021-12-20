package com.aggregation.crm.web.service;

import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.web.model.ReportResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface WebCaseService {

    List<CrmCase> getByFilter(String status, String providerName, String errorCode);

    List<ReportResponse> getReportByFilter(String status, String providerName, String errorCode);

}
