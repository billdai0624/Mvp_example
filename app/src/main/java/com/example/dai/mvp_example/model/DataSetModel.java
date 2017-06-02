package com.example.dai.mvp_example.model;

import java.util.List;

/**
 * Created by daijunwei on 2017/6/2.
 */

public class DataSetModel<T> {
    private String resource_id;
    private int limit;
    private int total;
    //fields;
    private List<T> records;

    public String getResource_id() {
        return resource_id;
    }

    public int getLimit() {
        return limit;
    }

    public int getTotal() {
        return total;
    }

    public List<T> getRecords() {
        return records;
    }
}
