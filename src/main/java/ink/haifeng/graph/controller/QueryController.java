package ink.haifeng.graph.controller;

import ink.haifeng.graph.entity.QueryResult;
import ink.haifeng.graph.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private QueryService queryService;

    @RequestMapping("/query")
    public QueryResult query(String host, int port, String gremlin) {
        return queryService.query(host, port, gremlin);
    }


}
