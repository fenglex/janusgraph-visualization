package ink.haifeng.graph.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author: haifeng
 * @Date: 2019-08-30 12:36
 */
@Data
public class QueryResult {
    private Set<Element> vertices = new HashSet<>(20);
    private Set<Element> edges = new HashSet<>(20);
    private Map<String, String> properties = new HashMap<String, String>(20);
    private String result;


    /**
     * 合并边和顶点的数据
     */
    public void merge() {
        for (Element edge : edges) {
            GraphEdge e = (GraphEdge) edge;
            this.vertices.add(e.getFrom());
            this.vertices.add(e.getTo());
        }
    }
}
