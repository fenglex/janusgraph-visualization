package ink.haifeng.graph;

import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.MessageSerializer;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.driver.ser.GryoMessageSerializerV3d0;
import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource;
import org.apache.tinkerpop.gremlin.structure.io.gryo.GryoMapper;
import org.janusgraph.graphdb.tinkerpop.JanusGraphIoRegistry;

import java.sql.Driver;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

public class QueryTest {
    public void before() {
        String host = "fenglex.com";
        int port = 8182;
        String remoteTraversalSourceName = "g";
        GryoMapper.Builder builder = GryoMapper.build().addRegistry(JanusGraphIoRegistry.getInstance());
        MessageSerializer serializer = new GryoMessageSerializerV3d0(builder);
        Cluster cluster = Cluster.build().
                addContactPoint(host).
                port(port).
                serializer(serializer).
                create();
        traversal().withRemote(DriverRemoteConnection.using(cluster, remoteTraversalSourceName));
    }
}
