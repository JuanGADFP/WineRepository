package org.juang.test.springboot.app.response;

import org.juang.test.springboot.app.models.Wine;

import java.util.*;

public class WineResponse {

    private List<Wine> wine;

    public List<Wine> getWine() {
        return wine;
    }

    public void setWine(List<Wine> wine) {
        this.wine = wine;
    }
}
