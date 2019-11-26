package com.fenglex.janus.query;

import com.alibaba.fastjson.JSON;
import com.fenglex.janus.Application;
import com.fenglex.janus.entity.vo.PropertyVo;
import com.fenglex.janus.service.QueryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: haifeng
 * @Date: 2019/11/26 16:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class QueryTest {

    @Autowired
    private QueryService queryService;

    @Test
    public void testQuery() {
        PropertyVo propertyVo = queryService.getValueMap("219.143.244.230", 8882, "g", "4248", true);
        System.out.println(JSON.toJSONString(propertyVo, true));
    }
}
