package com.aggregation.crm.web.model;

import com.aggregation.crm.repository.model.CrmCase;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ReportResponse {

    private String provider;

    private String errorCode;

    private Set<String> productsEffected;

    private List<CrmCase> supportCases;

    private Integer supportCaseNumber;

    public ReportResponse() {
    }

    public ReportResponse(String provider, String errorCode, List<CrmCase> supportCases) {
        this.provider = provider;
        this.errorCode = errorCode;
        this.supportCases = supportCases;
        this.supportCaseNumber = supportCases.size();
        this.productsEffected = supportCases.stream().map(CrmCase::getProductName).collect(Collectors.toSet());
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Set<String> getProductsEffected() {
        return productsEffected;
    }

    public void setProductsEffected(Set<String> productsEffected) {
        this.productsEffected = productsEffected;
    }

    public List<CrmCase> getSupportCases() {
        return supportCases;
    }

    public void setSupportCases(List<CrmCase> supportCases) {
        this.supportCases = supportCases;
    }

    public Integer getSupportCaseNumber() {
        return supportCaseNumber;
    }

    public void setSupportCaseNumber(Integer supportCaseNumber) {
        this.supportCaseNumber = supportCaseNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportResponse that = (ReportResponse) o;
        return Objects.equals(getProvider(), that.getProvider()) && Objects.equals(getErrorCode(), that.getErrorCode()) && Objects.equals(getProductsEffected(), that.getProductsEffected()) && Objects.equals(getSupportCases(), that.getSupportCases()) && Objects.equals(getSupportCaseNumber(), that.getSupportCaseNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProvider(), getErrorCode(), getProductsEffected(), getSupportCases(), getSupportCaseNumber());
    }
}
