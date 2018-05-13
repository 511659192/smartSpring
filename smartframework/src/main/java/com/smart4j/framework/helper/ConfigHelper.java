package com.smart4j.framework.helper;

import com.smart4j.framework.ConfigConstant;
import com.smart4j.framework.util.PropsUtil;

import java.util.Properties;

/**
 * Created by ym on 2018/5/11.
 */
public class ConfigHelper {

    private static final Properties CONFIG_PROPS = PropsUtil.load(ConfigConstant.CONFIG_FILE);

    public static String getJdbcDriver() {
        return PropsUtil.get(CONFIG_PROPS, ConfigConstant.JDBC_DRIVENAME);
    }

    public static String getJdbcUrl() {
        return PropsUtil.get(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    public static String getJdbcUserName() {
        return PropsUtil.get(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

    public static String getJdbcPassword() {
        return PropsUtil.get(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

    public static String getAppBasePackage() {
        return PropsUtil.get(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
    }

    public static String getAppJspPath() {
        return PropsUtil.get(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH);
    }

    public static String getAppAssetPath() {
        return PropsUtil.get(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH);
    }
}
