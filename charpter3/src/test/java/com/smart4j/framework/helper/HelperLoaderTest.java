package com.smart4j.framework.helper;

import com.smart4j.framework.annotation.Service;
import org.junit.Test;

/**
 * Created by ym on 2018/5/11.
 */
public class HelperLoaderTest {
    @Test
    public void init() throws Exception {
        HelperLoader.init();
        BeanHelper.getBean(Service.class);
    }

}
