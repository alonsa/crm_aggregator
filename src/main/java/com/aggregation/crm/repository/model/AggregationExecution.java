package com.aggregation.crm.repository.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(indexes = {@Index(name = "date_and_crm_producer", columnList = "crmProducer, date")})
public class AggregationExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private CrmProducer crmProducer;

    LocalDateTime date = LocalDateTime.now();

    public AggregationExecution() {
    }

    public AggregationExecution(CrmProducer crmProducer) {
        this.crmProducer = crmProducer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CrmProducer getCrmProducer() {
        return crmProducer;
    }

    public void setCrmProducer(CrmProducer crmProducer) {
        this.crmProducer = crmProducer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AggregationExecution that = (AggregationExecution) o;
        return Objects.equals(getId(), that.getId()) && getCrmProducer() == that.getCrmProducer() && Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCrmProducer(), getDate());
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
