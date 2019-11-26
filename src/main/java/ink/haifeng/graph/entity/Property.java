package ink.haifeng.graph.entity;

import lombok.Data;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;

/**
 * @Author: haifeng
 * @Date: 2019-09-05 11:46
 */
@Data
public class Property {
    private String key;
    private String value;
}
