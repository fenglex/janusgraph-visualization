package ink.haifeng.graph.controller;

import com.alibaba.fastjson.JSON;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

/**
 * @Author: haifeng
 * @Date: 2019-08-29 11:12
 */
@RestController
public class QueryController {

    @Autowired
    private Client client;

    @RequestMapping("/query")
    public String search(String k) {
        ResultSet resultSet = client.submit(k);
        Iterator<Result> iterator = resultSet.iterator();
        while (iterator.hasNext()) {
            Result next = iterator.next();
            System.out.println("tv:" + next.getString());
            Object obj = next.getObject();
            if (obj instanceof Long) {
                System.out.println(next.getLong());
            }else if (obj instanceof Vertex){
                Vertex vertex = next.getVertex();
                Object id = vertex.id();
            }
        }
        return JSON.toJSONString(resultSet);
    }
}
