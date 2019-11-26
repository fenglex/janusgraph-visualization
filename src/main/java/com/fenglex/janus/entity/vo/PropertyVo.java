package com.fenglex.janus.entity.vo;

import com.fenglex.janus.entity.Element;
import com.fenglex.janus.entity.GraphProperty;
import com.fenglex.janus.entity.KeyValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: haifeng
 * @Date: 2019/11/26 16:27
 */
@Data
public class PropertyVo {
    private String id;
    private String label;
    private boolean vertex;
    List<KeyValue> keyValues = new ArrayList<KeyValue>(50);

    public PropertyVo(Element element) {
        this.id = element.getId();
        this.label = element.getLabel();
        List<GraphProperty> properties = element.getProperties();
        for (GraphProperty property : properties) {
            String key = property.getKey();
            List<String> value = property.getValue();
            for (String v : value) {
                keyValues.add(new KeyValue(key, v));
            }
        }
    }
}
