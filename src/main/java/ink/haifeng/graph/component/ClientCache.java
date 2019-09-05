package ink.haifeng.graph.component;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import ink.haifeng.graph.util.GremlinUtil;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.springframework.context.annotation.Bean;

/**
 * @Author: haifeng
 * @Date: 2019-09-05 17:33
 */

public class ClientCache {

    private final static int EXPIRED_TIME = 1000 * 60 * 5;

    private final static TimedCache<String, Cluster> cache = CacheUtil.newTimedCache(EXPIRED_TIME);

    private String key(String host, int port) {
        return String.format("%s:%s", host, port);
    }

    public Client get(String host, int port) {
        String key = key(host, port);
        Cluster cluster = cache.get(key);
        if (cluster == null) {
            cluster = GremlinUtil.cluster(host, port);
            //cache.put(key, client);
        }
        return cluster.connect().init();
    }

}
