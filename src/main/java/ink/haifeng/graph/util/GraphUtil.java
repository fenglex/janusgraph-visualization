package ink.haifeng.graph.util;

import cn.hutool.core.date.DateUtil;
import ink.haifeng.graph.entity.GraphEdge;
import ink.haifeng.graph.entity.GraphVertex;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Iterator;

/**
 * @Author: haifeng
 * @Date: 2019-08-30 17:00
 */
public class GraphUtil {


    public static GraphVertex convert(Vertex vertex) {
        GraphVertex graphVertex = new GraphVertex();

        graphVertex.setId(vertex.id().toString());
        graphVertex.setLabel(vertex.label());
        Iterator<VertexProperty<Object>> properties = vertex.properties();
        while (properties.hasNext()) {
            VertexProperty<Object> property = properties.next();
            String key = property.key();
            Object value = property.value();
            String v;
            if (value instanceof Data) {
                v = DateUtil.formatDateTime((Date) value);
            } else {
                v = value.toString();
            }
            graphVertex.putProperty(key, v);
        }
        return graphVertex;
    }

    public static GraphEdge convert(Edge edge) {
        GraphEdge graphEdge = new GraphEdge();
        graphEdge.setId(edge.id().toString());
        graphEdge.setLabel(edge.label());
        Iterator<Property<Object>> properties = edge.properties();
        while (properties.hasNext()) {
            Property<Object> property = properties.next();
            String key = property.key();
            Object value = property.value();
            graphEdge.putProperty(key, value instanceof Date ? DateUtil.formatDateTime((Date) value) : value.toString());
        }
        Vertex inVertex = edge.inVertex();
        Vertex outVertex = edge.outVertex();
        GraphVertex inGraphVertex = convert(inVertex);
        GraphVertex outGraphVertex = convert(outVertex);
        graphEdge.setFrom(outGraphVertex);
        graphEdge.setTo(inGraphVertex);
        return graphEdge;
    }

}
