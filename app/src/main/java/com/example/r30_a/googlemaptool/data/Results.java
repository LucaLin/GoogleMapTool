package com.example.r30_a.googlemaptool.data;

import com.google.gson.internal.LinkedTreeMap;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by R30-A on 2019/3/15.
 */

public class Results<T> {

    int offset;
    int limit;
    int count;
    String sort;
    List<T> results;
    LinkedTreeMap<T,T> mapResults;

    public LinkedTreeMap<T, T> getMapResults() {
        return mapResults;
    }

    public void setMapResults(LinkedTreeMap<T, T> mapResults) {
        this.mapResults = mapResults;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
