package ink.haifeng.graph.component;

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.MessageSerializer;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.driver.ser.GryoMessageSerializerV3d0;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.io.gryo.GryoMapper;
import org.janusgraph.graphdb.tinkerpop.JanusGraphIoRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

/**
 * @Author: haifeng
 * @Date: 2019-08-29 11:17
 */
@Component
public class GraphBean {


    private String host = "localhost";
    private int port = 8182;

    @Bean
    public Cluster cluster() {
        GryoMapper.Builder builder = GryoMapper.build().addRegistry(JanusGraphIoRegistry.getInstance());
        MessageSerializer serializer = new GryoMessageSerializerV3d0(builder);
        return Cluster.build().
                addContactPoint(host).
                port(port).
                serializer(serializer).
                create();
    }

    @Bean
    public GraphTraversalSource source(Cluster cluster) {
        return traversal().withRemote(DriverRemoteConnection.using(cluster, "g"));
    }

    @Bean
    public Client client(Cluster cluster) {
        return cluster.connect().init();
    }
}
