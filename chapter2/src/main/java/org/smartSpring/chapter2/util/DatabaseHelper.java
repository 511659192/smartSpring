package org.smartSpring.chapter2.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Created by ym on 2018/5/9.
 */
@Slf4j
public class DatabaseHelper {

    private final static String driverName;
    private final static String url;
    private final static String username;
    private final static String password;

    private final static QueryRunner QUERY_RUNNER = new QueryRunner();

    private final static ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<Connection>();

    private final static BasicDataSource dataSouce;

    static {
        Properties properties = PropsUtil.load("config.properties");
        driverName = properties.getProperty("jdbc.driveName");
        url = properties.getProperty("jdbc.url");
        username = properties.getProperty("jdbc.username");
        password = properties.getProperty("jdbc.password");
        dataSouce = new BasicDataSource();
        dataSouce.setDriverClassName(driverName);
        dataSouce.setUrl(url);
        dataSouce.setUsername(username);
        dataSouce.setPassword(password);
    }

    public static Connection getConnect() throws SQLException {
        Connection connection = CONNECTION_THREAD_LOCAL.get();
        if (connection == null) {
//            connection = DriverManager.getConnection(url, username, password);
            connection = dataSouce.getConnection();
            CONNECTION_THREAD_LOCAL.set(connection);
        }
        return connection;
    }

    public static void closeConnect(Connection conn) throws SQLException {
//        if (conn != null) {
//            try {
//                conn.close();
//            } finally {
//                CONNECTION_THREAD_LOCAL.remove();
//            }
//        }
    }

    public static void closeConnect() throws SQLException {
//        Connection connection = CONNECTION_THREAD_LOCAL.get();
//        if (connection != null) {
//            connection.close();
//        }
    }

    public static <T> List<T> getList(Class<T> clazz, String sql, Object... params) throws SQLException {
        Connection connection = getConnect();
        try {
            return getList(clazz, connection, sql, params);
        } finally {
            closeConnect();
        }
    }

    public static <T> List<T> getList(Class<T> clazz, Connection connection, String sql, Object... params) throws SQLException {
        List<T> list;
        try {
            list = QUERY_RUNNER.query(connection, sql, new BeanListHandler<T>(clazz), params);
        } finally {
            closeConnect(connection);
        }
        return list;
    }
}
