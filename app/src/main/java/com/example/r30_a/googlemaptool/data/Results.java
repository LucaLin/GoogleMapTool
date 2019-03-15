package com.example.r30_a.googlemaptool.data;

import java.util.List;

/**
 * Created by R30-A on 2019/3/15.
 */

public class Results<T> {

    int offset;
    int limit;
    int count;
    String sort;
    List<SpeedCamera> results;

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

    public List<SpeedCamera> getResults() {
        return results;
    }

    public void setResults(List<SpeedCamera> results) {
        this.results = results;
    }
}
