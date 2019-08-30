package ink.haifeng.graph.service;

import ink.haifeng.graph.entity.GraphEdge;
import ink.haifeng.graph.entity.GraphVertex;
import ink.haifeng.graph.entity.QueryResult;

/**
 * @Author: haifeng
 * @Date: 2019-08-30 16:49
 */
public interface QueryService {

    GraphVertex queryVertex(String id);

    GraphEdge queryEdge(String id);

    QueryResult query(String gremlin);
}
