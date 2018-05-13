package org.smartSpring.chapter2.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ym on 2018/5/9.
 */
@Slf4j
public class PropsUtil {

    public static Properties load(String fileName) {
        Properties properties = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + "not found");
            }
            properties = new Properties();
            properties.load(is);
        } catch (Exception e) {
            log.error("load properties file failed", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("close is failed");
                }
            }
        }
        return properties;
    }

    private static String get(Properties properties, String key) {
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        }
        return null;
    }
}
