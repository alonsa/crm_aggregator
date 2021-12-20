package com.aggregation.crm.web.controller;


import com.aggregation.crm.web.client.CollectorClient;
import com.aggregation.crm.web.service.WebCaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CaseController.class)
@PropertySource("classpath:web-server.properties")
public class CaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WebCaseService caseService;

    @MockBean
    private CollectorClient controllerClient;

    @Test
    public void getReportByFilterStatusClosedAndOtherValuesAreNull() throws Exception {
        MockHttpServletRequestBuilder request = get("/case/report?status=Closed");
        this.mockMvc.perform(request).andDo(print()).andExpect(status().isOk());
        verify(caseService).getReportByFilter("Closed", null, null);
    }

    @Test
    public void getReportByFilterAllValuesFilled() throws Exception {
        MockHttpServletRequestBuilder request = get("/case/report?status=Open&providerName=pName&errorCode=eCode");
        this.mockMvc.perform(request).andDo(print()).andExpect(status().isOk());
        verify(caseService).getReportByFilter("Open", "pName", "eCode");
    }

    @Test
    public void executeAggregation() throws Exception {
        MockHttpServletRequestBuilder request = put("/case/aggregate/all");
        this.mockMvc.perform(request).andDo(print()).andExpect(status().isCreated());
        verify(controllerClient).executeAggregation();
    }
}
