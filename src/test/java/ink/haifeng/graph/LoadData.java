package ink.haifeng.graph;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.example.GraphOfTheGodsFactory;

/**
 * @Author: haifeng
 * @Date: 2019-08-30 10:02
 */

public class LoadData {
    public static void main(String[] args) {
        JanusGraph graph = JanusGraphFactory.open("/Users/sundays/dev/github/janusgraph-visualization/src/main/resources/janusgraph" +
                ".properties");
        GraphTraversalSource g = graph.traversal();
        g.V().drop().iterate();
        g.tx().commit();
        graph.tx().commit();
        GraphOfTheGodsFactory.load(graph);
    }
}
