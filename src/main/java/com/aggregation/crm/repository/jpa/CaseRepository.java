package com.aggregation.crm.repository.jpa;

import com.aggregation.crm.repository.model.CrmCase;
import com.aggregation.crm.repository.model.CrmProducer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CaseRepository extends JpaRepository<CrmCase, String> {

    @Query("select c from CrmCase c where " +
            "(:status is null or c.status = :status) AND " +
            "(:provider is null or c.provider = :provider) AND " +
            "(:errorCode is null or c.errorCode = :errorCode)")
    List<CrmCase> findByStatusProviderAndErrorCode(@Param("status") String status, @Param("provider") String provider, @Param("errorCode") String errorCode);

    @Query("select max(c.modifiedDate) from CrmCase c where c.crmProducer = :crmProducer")
    LocalDateTime getLastModifiedDateByCrmProducer(@Param("crmProducer") CrmProducer crmProducer);
}
