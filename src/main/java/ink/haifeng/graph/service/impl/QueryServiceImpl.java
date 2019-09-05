package ink.haifeng.graph.service.impl;

import cn.hutool.core.date.DateUtil;
import ink.haifeng.graph.common.Constant;
import ink.haifeng.graph.entity.GraphEdge;
import ink.haifeng.graph.entity.GraphVertex;
import ink.haifeng.graph.entity.QueryResult;
import ink.haifeng.graph.service.QueryService;
import ink.haifeng.graph.util.GraphUtil;
import ink.haifeng.graph.util.GremlinUtil;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: haifeng
 * @Date: 2019-08-30 16:49
 */
@Service
public class QueryServiceImpl implements QueryService {


    private GraphVertex queryVertex(Client client, String id) {
        String gremlin = String.format("g.V('%s')", id);
        ResultSet set = client.submit(gremlin);
        Iterator<Result> iterator = set.iterator();
        while (iterator.hasNext()) {
            Result next = iterator.next();
            Vertex vertex = next.getVertex();
            return GraphUtil.convert(vertex);
        }
        return null;
    }

    private GraphEdge queryEdge(Client client, String id) {
        String gremlin = String.format("g.E('%s')", id);
        ResultSet set = client.submit(gremlin);
        Iterator<Result> iterator = set.iterator();
        while (iterator.hasNext()) {
            Result next = iterator.next();
            Edge edge = next.getEdge();
            GraphEdge graphEdge = GraphUtil.convert(edge);
            GraphVertex graphVertex = queryVertex(client, edge.inVertex().id().toString());
            graphEdge.setTarget(graphVertex.getId());
            graphEdge.setTo(graphVertex);
            GraphVertex outGraphVertex = queryVertex(client, edge.outVertex().id().toString());
            graphEdge.setFrom(outGraphVertex);
            graphEdge.setSource(outGraphVertex.getId());
            return graphEdge;
        }
        return null;
    }

    @Override
    public QueryResult query(String host, int port, String gremlin) {
        Client client = GremlinUtil.client(host, port);
        ResultSet set = client.submit(gremlin);
        QueryResult result = new QueryResult();
        StringBuilder builder = new StringBuilder();

        Iterator<Result> iterator = set.iterator();
        while (iterator.hasNext()) {
            Result next = iterator.next();
            builder.append(next.getString()).append(Constant.RESULT_SPLIT);
            Object obj = next.getObject();
            if (obj instanceof Vertex) {
                Vertex vertex = next.getVertex();
                result.getVertices().add(GraphUtil.convert(vertex));
            } else if (obj instanceof Edge) {
                Edge edge = next.getEdge();
                GraphEdge graphEdge = queryEdge(client, edge.id().toString());
                result.getEdges().add(graphEdge);
            } else if (obj instanceof VertexProperty) {
                VertexProperty<Object> property = next.getVertexProperty();
                Map<String, String> map = new HashMap<>(1);
                Object value = property.value();
                map.put(property.key(), value instanceof Data ? DateUtil.formatDateTime((Date) value) : value.toString());
                result.getProperties().putAll(map);
            } else if (obj instanceof Property) {
                Property<Object> property = next.getProperty();
                Map<String, String> map = new HashMap<>(1);
                String key = property.key();
                Object value = property.value();
                if (value instanceof Date) {
                    map.put(key, DateUtil.formatDateTime((Date) value));
                } else {
                    map.put(key, value.toString());
                }
                result.setProperties(map);
            } else if (obj instanceof Path) {
                Path path = next.getPath();
                for (Object next1 : path) {
                    if (next1 instanceof Vertex) {
                        Vertex vertex = (Vertex) next1;
                        GraphVertex graphVertex = queryVertex(client, vertex.id().toString());
                        result.getVertices().add(graphVertex);
                    } else {
                        Edge edge = (Edge) next1;
                        GraphEdge graphEdge = queryEdge(client, edge.id().toString());
                        result.getEdges().add(graphEdge);
                    }
                }
            }
        }
        result.merge();
        result.setResult(builder.toString());
        client.close();
        return result;
    }
}
