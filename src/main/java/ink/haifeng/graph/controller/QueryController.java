package ink.haifeng.graph.controller;

import com.alibaba.fastjson.JSON;
import ink.haifeng.graph.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: haifeng
 * @Date: 2019-08-29 11:12
 */
@RestController
public class QueryController {

    @Autowired
    private QueryService queryService;

    @RequestMapping("/query")
    public String search(String q) {
        return JSON.toJSONString(queryService.query(q));
    }


}
