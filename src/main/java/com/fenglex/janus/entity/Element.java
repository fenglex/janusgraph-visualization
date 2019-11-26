package com.fenglex.janus.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: haifeng
 * @Date: 2019-08-29 18:21
 */

@Data
public class Element {
    private String id;
    private String label;
    private List<GraphProperty> properties = new ArrayList<>(10);

    public void putProperty(GraphProperty property) {
        this.properties.add(property);
    }

}
