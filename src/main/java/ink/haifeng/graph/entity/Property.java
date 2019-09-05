package ink.haifeng.graph.entity;

import lombok.Data;
import org.janusgraph.core.Cardinality;

/**
 * @Author: haifeng
 * @Date: 2019-09-05 11:46
 */
@Data
public class Property {
    private String type;
    private Cardinality cardinality;
    private String key;
    private String value;
    private String property;
}
