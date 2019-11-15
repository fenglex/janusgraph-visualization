package ink.haifeng.graph.entity;

import com.alibaba.fastjson.annotation.JSONField;
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
    private List<Property> properties = new ArrayList<>(5);

    public void putProperty(String key, String value) {
        Property prop = new Property();
        prop.setKey(key);
        prop.setValue(value);
        this.properties.add(prop);
    }

    public void putProperty(Property property) {
        this.properties.add(property);
    }

}
