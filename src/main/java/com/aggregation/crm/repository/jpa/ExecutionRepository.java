package com.aggregation.crm.repository.jpa;

import com.aggregation.crm.repository.model.AggregationExecution;
import com.aggregation.crm.repository.model.CrmProducer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ExecutionRepository extends JpaRepository<AggregationExecution, Integer> {

    @Query("select max(a.date) from AggregationExecution a where a.crmProducer = :crmProducer")
    LocalDateTime getLastExecutionDate(@Param("crmProducer") CrmProducer crmProducer);

}
