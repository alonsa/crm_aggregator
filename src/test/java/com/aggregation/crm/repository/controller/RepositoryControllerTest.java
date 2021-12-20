package com.aggregation.crm.repository.controller;


import com.aggregation.crm.repository.model.CaseStatus;
import com.aggregation.crm.repository.model.CrmProducer;
import com.aggregation.crm.repository.service.CaseService;
import com.aggregation.crm.repository.service.ExecutionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RepositoryController.class)
@PropertySource("classpath:repository-server.properties")
public class RepositoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CaseService mockCaseService;

    @MockBean
    private ExecutionService mockExecutionService;

    @Test
    public void getCrmCasesByFilterStatusClosedAndOtherValuesAreNull() throws Exception {
        MockHttpServletRequestBuilder request = get("/repository/case/filter?status=CLOSED");
        this.mockMvc.perform(request).andDo(print()).andExpect(status().isOk());
        verify(mockCaseService).getByFilter(CaseStatus.CLOSED, null, null);
    }

    @Test
    public void getCrmCasesReportByFilterAllValuesFilled() throws Exception {
        MockHttpServletRequestBuilder request = get("/repository/case/filter?status=OPEN&providerName=pName&errorCode=eCode");
        this.mockMvc.perform(request).andDo(print()).andExpect(status().isOk());
        verify(mockCaseService).getByFilter(CaseStatus.OPEN, "pName", "eCode");
    }

    @Test
    public void getLastModifiedDateByCrmProducer() throws Exception {
        MockHttpServletRequestBuilder request = get("/repository/case/modified/last?crmProducer=BANANA");
        this.mockMvc.perform(request).andDo(print()).andExpect(status().isOk());
        verify(mockCaseService).getLastModifiedDateByCrmProducer(CrmProducer.BANANA);
    }

    @Test
    public void getLastExecutionDate() throws Exception {
        MockHttpServletRequestBuilder request = get("/repository/execution/last?crmProducer=BANANA");
        this.mockMvc.perform(request).andDo(print()).andExpect(status().isOk());
        verify(mockExecutionService).getLastDateByCrmProducer(CrmProducer.BANANA);
    }

}
