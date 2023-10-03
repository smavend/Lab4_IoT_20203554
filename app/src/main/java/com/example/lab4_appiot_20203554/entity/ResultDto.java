package com.example.lab4_appiot_20203554.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultDto implements Serializable {
    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }
}
