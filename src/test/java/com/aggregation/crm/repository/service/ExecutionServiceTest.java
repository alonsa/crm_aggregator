package com.aggregation.crm.repository.service;

import com.aggregation.crm.repository.exception.DaoException;
import com.aggregation.crm.repository.jpa.ExecutionRepository;
import com.aggregation.crm.repository.model.AggregationExecution;
import com.aggregation.crm.repository.model.CrmProducer;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class ExecutionServiceTest {

    private final ExecutionRepository mockExecutionRepository = mock(ExecutionRepository.class);
    private final ExecutionService webCaseService = new ExecutionServiceImpl(mockExecutionRepository);

    @Test
    public void successSave() throws DaoException {
        AggregationExecution aggregationExecution = new AggregationExecution(CrmProducer.BANANA);
        webCaseService.save(aggregationExecution);
        verify(mockExecutionRepository).save(aggregationExecution);
    }

    @Test
    public void failSaveCrmCases() {
        AggregationExecution aggregationExecution = new AggregationExecution(CrmProducer.BANANA);
        when(mockExecutionRepository.save(aggregationExecution)).thenThrow(new RuntimeException("test exception"));
        assertThrows(DaoException.class, () -> webCaseService.save(aggregationExecution));
    }

    @Test
    public void successGetLastModifiedDateByCrmProducer() throws DaoException {
        LocalDateTime date = LocalDateTime.now();
        when(mockExecutionRepository.getLastExecutionDate(CrmProducer.BANANA)).thenReturn(date);
        LocalDateTime lastModifiedDate = webCaseService.getLastDateByCrmProducer(CrmProducer.BANANA);

        assertEquals(date, lastModifiedDate);
    }

    @Test
    public void failGetLastModifiedDateByCrmProducer() throws DaoException {
        when(mockExecutionRepository.getLastExecutionDate(CrmProducer.BANANA)).thenThrow(new RuntimeException("test exception"));
        assertThrows(DaoException.class, () -> webCaseService.getLastDateByCrmProducer(CrmProducer.BANANA));
    }

}
