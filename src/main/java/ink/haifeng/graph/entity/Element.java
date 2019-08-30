package ink.haifeng.graph.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: haifeng
 * @Date: 2019-08-29 18:21
 */

@Data
public class Element {
    private String id;
    private String label;
    private Map<String, String> properties = new HashMap<>(5);

    public void putProperty(String key, String value) {
        this.properties.put(key, value);
    }

}
