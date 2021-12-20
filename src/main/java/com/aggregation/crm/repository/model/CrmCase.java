package com.aggregation.crm.repository.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(
        indexes = {
                @Index(name = "modified_date_and_crm_producer", columnList = "crmProducer, modifiedDate"),
                @Index(name = "status", columnList = "status"),
                @Index(name = "provider", columnList = "provider"),
                @Index(name = "errorCode", columnList = "errorCode")
        })
public class CrmCase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer caseId;

    @Enumerated(EnumType.STRING)
    private CrmProducer crmProducer;

    private String customerId;

    private String provider;

    private String errorCode;

    @Enumerated(EnumType.STRING)
    private CaseStatus status;

    @JsonFormat(pattern = "M/d/yyyy H:mm")
    private LocalDateTime creationDate;

    @JsonFormat(pattern = "M/d/yyyy H:mm")
    private LocalDateTime modifiedDate;

    private String productName;

    public CrmCase() {
    }

    public CrmCase(Integer caseId, CrmProducer crmProducer, String customerId, String provider, String errorCode,
                   CaseStatus status, LocalDateTime creationDate, LocalDateTime modifiedDate, String productName) {
        this.caseId = caseId;
        this.crmProducer = crmProducer;
        this.customerId = customerId;
        this.provider = provider;
        this.errorCode = errorCode;
        this.status = status;
        this.creationDate = creationDate;
        this.modifiedDate = modifiedDate;
        this.productName = productName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCaseId() {
        return caseId;
    }

    @JsonSetter("Case ID")
    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public CrmProducer getCrmProducer() {
        return crmProducer;
    }

    @JsonSetter("crm_producer")
    public void setCrmProducer(CrmProducer crmProducer) {
        this.crmProducer = crmProducer;
    }

    public String getCustomerId() {
        return customerId;
    }

    @JsonSetter("Customer ID")
    @JsonAlias("Customer_ID")
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public String getProvider() {
        return provider;
    }

    @JsonSetter("Provider")
    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @JsonSetter("CREATED_ERROR_CODE")
    public void setErrorCode(String createdErrorCode) {
        this.errorCode = createdErrorCode;
    }

    public CaseStatus getStatus() {
        return status;
    }

    @JsonSetter("STATUS")
    public void setStatus(CaseStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @JsonSetter("TICKET_CREATION_DATE")
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    @JsonSetter("LAST_MODIFIED_DATE")
    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getProductName() {
        return productName;
    }

    @JsonSetter("PRODUCT_NAME")
    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrmCase crmCase = (CrmCase) o;
        return Objects.equals(getId(), crmCase.getId()) && Objects.equals(getCaseId(), crmCase.getCaseId()) && getCrmProducer() == crmCase.getCrmProducer() && Objects.equals(getCustomerId(), crmCase.getCustomerId()) && Objects.equals(getProvider(), crmCase.getProvider()) && Objects.equals(getErrorCode(), crmCase.getErrorCode()) && getStatus() == crmCase.getStatus() && Objects.equals(getCreationDate(), crmCase.getCreationDate()) && Objects.equals(getModifiedDate(), crmCase.getModifiedDate()) && Objects.equals(getProductName(), crmCase.getProductName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCaseId(), getCrmProducer(), getCustomerId(), getProvider(), getErrorCode(), getStatus(), getCreationDate(), getModifiedDate(), getProductName());
    }
}


