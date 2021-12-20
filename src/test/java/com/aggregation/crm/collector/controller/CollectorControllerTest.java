package com.aggregation.crm.collector.controller;


import com.aggregation.crm.collector.service.BananaCollectorService;
import com.aggregation.crm.collector.service.StrawberryCollectorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CollectorController.class)
@PropertySource("classpath:collector-server.properties")
public class CollectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BananaCollectorService bananaCollectorService;

    @MockBean
    StrawberryCollectorService strawberryCollectorService;

    @Test
    public void getReportByFilterStatusClosedAndOtherValuesAreNull() throws Exception {
        MockHttpServletRequestBuilder request = put("/collector/aggregate/all");
        this.mockMvc.perform(request).andDo(print()).andExpect(status().isCreated());
        verify(bananaCollectorService).collectByDemand();
        verify(strawberryCollectorService).collectByDemand();
    }

}
