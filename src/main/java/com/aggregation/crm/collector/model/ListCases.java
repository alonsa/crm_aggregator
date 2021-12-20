package com.aggregation.crm.collector.model;

import com.aggregation.crm.repository.model.CrmCase;

import java.util.List;
import java.util.Objects;

public class ListCases<E extends CrmCase> {
    private List<E> date = List.of();

    public ListCases() {
    }

    public ListCases(List<E> date) {
        this.date = date;
    }

    public List<E> getDate() {
        return date;
    }

    public void setDate(List<E> date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListCases that = (ListCases) o;
        return Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate());
    }
}
