package com.fenglex.janus.controller;

import com.fenglex.janus.entity.GraphEdge;
import com.fenglex.janus.entity.GraphVertex;
import com.fenglex.janus.entity.QueryResult;
import com.fenglex.janus.service.QueryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: haifeng
 * @Date: 2019-08-29 11:12
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class QueryController {

    private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @RequestMapping("/query")
    public QueryResult query(String host, int port, String sourceName, String gremlin) {
        return queryService.query(host, port, gremlin, sourceName);
    }

    @RequestMapping("/vertex")
    public GraphVertex queryVertex(String host, int port, String sourceName, String id) {
        return (GraphVertex) queryService.getElement(host, port, sourceName, id, true);
    }

    @RequestMapping("/edge")
    public GraphEdge queryEdge(String host, int port, String sourceName, String id) {
        return (GraphEdge) queryService.getElement(host, port, sourceName, id, false);
    }


}
