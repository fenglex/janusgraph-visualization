package com.fenglex.janus.service;

import com.fenglex.janus.entity.Element;
import com.fenglex.janus.entity.QueryResult;
import com.fenglex.janus.entity.vo.PropertyVo;

/**
 * @Author: haifeng
 * @Date: 2019-08-30 16:49
 */
public interface QueryService {


    QueryResult query(String host, int port, String gremlin, String sourceName);

    PropertyVo getValueMap(String host, int port, String sourceName, String id, boolean vertex);

}
