package com.aggregation.crm.collector.model;

import com.aggregation.crm.repository.model.CrmCase;

import java.util.List;
import java.util.Objects;

public class ListCases<E extends CrmCase> {
    private List<E> data = List.of();

    public ListCases() {
    }

    public ListCases(List<E> date) {
        this.data = date;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListCases that = (ListCases) o;
        return Objects.equals(getData(), that.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getData());
    }
}
