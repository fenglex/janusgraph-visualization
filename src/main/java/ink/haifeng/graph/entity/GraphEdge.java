package ink.haifeng.graph.entity;

import lombok.Data;

/**
 * @Author: haifeng
 * @Date: 2019-08-30 16:50
 */
@Data
public class GraphEdge extends Element {
    private GraphVertex from;
    private GraphVertex to;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GraphEdge edge = (GraphEdge) o;
        return this.getId().equals(edge.getId());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}