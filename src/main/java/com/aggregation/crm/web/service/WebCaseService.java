package com.aggregation.crm.web.service;

import com.aggregation.crm.collector.execption.HttpClientException;
import com.aggregation.crm.web.execption.ParseException;
import com.aggregation.crm.web.model.ReportResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface WebCaseService {

    List<ReportResponse> getReportByFilter(String status, String providerName, String errorCode) throws HttpClientException, ParseException;

}
