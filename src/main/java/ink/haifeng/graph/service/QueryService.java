package ink.haifeng.graph.service;

import ink.haifeng.graph.entity.QueryResult;

/**
 * @Author: haifeng
 * @Date: 2019-08-30 16:49
 */
public interface QueryService {


    QueryResult query(String host,int port,String gremlin);
}
