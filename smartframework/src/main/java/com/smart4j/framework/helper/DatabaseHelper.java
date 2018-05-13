package com.smart4j.framework.helper;

import com.smart4j.framework.helper.ConfigHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ym on 2018/5/9.
 */
@Slf4j
public class DatabaseHelper {

    private final static String driverName;
    private final static String url;
    private final static String username;
    private final static String password;

    private final static QueryRunner QUERY_RUNNER;

    private final static ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<Connection>();

    static {
        driverName = ConfigHelper.getJdbcDriver();
        url = ConfigHelper.getJdbcUrl();
        username = ConfigHelper.getJdbcUserName();
        password = ConfigHelper.getJdbcPassword();
        BasicDataSource dataSouce = new BasicDataSource();
        dataSouce.setDriverClassName(driverName);
        dataSouce.setUrl(url);
        dataSouce.setUsername(username);
        dataSouce.setPassword(password);
        QUERY_RUNNER = new QueryRunner(dataSouce);
    }

    public static Connection getConnect() throws SQLException {
        Connection connection = CONNECTION_THREAD_LOCAL.get();
        if (connection == null) {
            connection = QUERY_RUNNER.getDataSource().getConnection();
            CONNECTION_THREAD_LOCAL.set(connection);
        }
        return connection;
    }

    public static <T> List<T> getList(Class<T> clazz, String sql, Object... params) throws SQLException {
        return QUERY_RUNNER.query(sql, new BeanListHandler<T>(clazz), params);
    }

    public static int update(String sql) throws SQLException {
        return QUERY_RUNNER.update(getConnect(), sql);
    }

    public static void beginTransaction() throws SQLException {
        System.out.println("=============beginTransaction==============");
        Connection connection = getConnect();
        if (connection != null) {
            connection.setAutoCommit(false);
        }
    }

    public static void commitTransaction() throws SQLException {
        System.out.println("=============commitTransaction==============");
        Connection connect = getConnect();
        if (connect != null) {
            connect.commit();
            connect.close();
            CONNECTION_THREAD_LOCAL.remove();
        }
    }

    public static void rollbackTransaction() throws SQLException {
        Connection connect = getConnect();
        if (connect != null) {
            connect.rollback();
            connect.close();
            CONNECTION_THREAD_LOCAL.remove();
        }
    }
}
