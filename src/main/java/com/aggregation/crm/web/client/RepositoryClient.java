package com.aggregation.crm.web.client;

import com.aggregation.crm.repository.model.CrmCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RepositoryClient {

    List<CrmCase> findByStatusAndProvider(String status, String provider, String errorCode);

}
