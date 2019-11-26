package com.fenglex.janus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: haifeng
 * @Date: 2019-09-05 11:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphProperty {
    private VertexProperty.Cardinality cardinality;
    private String key;
    private List<String> value = new ArrayList<>(5);

    public void addValue(String value) {
        this.value.add(value);
    }
}
