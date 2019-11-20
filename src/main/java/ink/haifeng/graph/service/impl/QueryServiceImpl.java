package ink.haifeng.graph.service.impl;

import cn.hutool.core.date.DateUtil;
import ink.haifeng.graph.common.Constant;
import ink.haifeng.graph.component.ClusterCache;
import ink.haifeng.graph.entity.Element;
import ink.haifeng.graph.entity.GraphEdge;
import ink.haifeng.graph.entity.GraphVertex;
import ink.haifeng.graph.entity.QueryResult;
import ink.haifeng.graph.service.QueryService;
import ink.haifeng.graph.util.GraphUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: haifeng
 * @Date: 2019-08-30 16:49
 */
@Service
@Slf4j
public class QueryServiceImpl implements QueryService {


    @Autowired
    private ClusterCache clusterCache;


    private Client getClient(String host, int port) {
        Client client = clusterCache.get(host, port);
        if (client == null) {
            client = clusterCache.put(host, port);
        }
        return client;
    }

    @Override
    public QueryResult query(String host, int port, String gremlin, String sourceName) {
        log.info("query:{}", gremlin);
        Client client = getClient(host, port);
        ResultSet set = client.submit(gremlin);
        QueryResult result = new QueryResult();
        StringBuilder builder = new StringBuilder();
        Iterator<Result> iterator = set.iterator();
        String errorMessage = null;
        try {
            while (iterator.hasNext()) {
                Result next = iterator.next();
                builder.append(next.getString()).append(Constant.RESULT_SPLIT);
                Object obj = next.getObject();
                if (obj instanceof Vertex) {
                    Vertex vertex = next.getVertex();
                    GraphVertex graphVertex = convert(vertex);
                    result.getVertices().add(graphVertex);
                } else if (obj instanceof Edge) {
                    Edge edge = next.getEdge();
                    GraphEdge graphEdge = convert(edge);
                    result.getEdges().add(graphEdge);
                } else if (obj instanceof VertexProperty) {
                    VertexProperty<Object> property = next.getVertexProperty();
                    Map<String, String> map = new HashMap<>(1);
                    Object value = property.value();
                    map.put(property.key(), value instanceof Date ? DateUtil.formatDateTime((Date) value) : value.toString());
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
                            GraphVertex graphVertex = convert(vertex);
                            result.getVertices().add(graphVertex);
                        } else {
                            Edge edge = (Edge) next1;
                            GraphEdge graphEdge = convert(edge);
                            result.getEdges().add(graphEdge);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage = e.getMessage();
        }
        result.merge();
        if (errorMessage == null) {
            String rs = builder.toString().isEmpty() ? "无结果" : builder.toString();
            result.setResult(rs);
        } else {
            result.setResult(errorMessage);
        }
        return result;
    }


    @Override
    public Element getElement(String host, int port, String sourceName, String id, boolean isVertex) {
        String gremlin = isVertex ? String.format("%s.V(%s)", sourceName, id) : String.format("%s.E(%s)", sourceName, id);
        Client client = getClient(host, port);
        log.info("query gremlin:{}", gremlin);
        ResultSet set = client.submit(gremlin);
        Iterator<Result> iterator = set.iterator();
        if (iterator.hasNext()) {
            if (isVertex) {
                Result next = iterator.next();
                Vertex vertex = next.getVertex();
                return GraphUtil.convert(vertex);
            } else {
                Result next = iterator.next();
                Edge edge = next.getEdge();
                return GraphUtil.convert(edge);
            }
        } else {
            return null;
        }
    }

    private GraphEdge convert(Edge edge) {
        GraphEdge graphEdge = new GraphEdge();
        graphEdge.setId(edge.id().toString());
        graphEdge.setLabel(edge.label());
        graphEdge.setFrom(edge.inVertex().id().toString());
        graphEdge.setTo(edge.outVertex().id().toString());

        GraphVertex inVertex = new GraphVertex();
        inVertex.setId(edge.inVertex().id().toString());
        inVertex.setLabel(edge.inVertex().label());
        graphEdge.setTarget(inVertex);

        GraphVertex outVertex = new GraphVertex();
        outVertex.setId(edge.outVertex().id().toString());
        outVertex.setLabel(edge.outVertex().id().toString());
        graphEdge.setSource(outVertex);
        return graphEdge;
    }

    private GraphVertex convert(Vertex vertex) {
        GraphVertex graphVertex = new GraphVertex();
        graphVertex.setId(vertex.id().toString());
        graphVertex.setLabel(vertex.label());
        return graphVertex;
    }

}
