package com.smart4j.framework.util;

import com.alibaba.fastjson.JSON;
import com.smart4j.framework.util.ClassUtil;
import org.junit.Test;

import java.util.Set;

/**
 * Created by ym on 2018/5/11.
 */
public class ClassUtilTest {
    @Test
    public void getClassLoader() throws Exception {
    }

    @Test
    public void loadClass() throws Exception {
    }

    @Test
    public void getClassSet() throws Exception {
        Set<Class<?>> classSet = ClassUtil.getClassSet("org/apache/commons/lang3");
        System.out.println(classSet.size());
        String jsonString = JSON.toJSONString(classSet);
        System.out.println(jsonString);;
    }
}
