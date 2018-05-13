package org.smartSpring.chapter2.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.smartSpring.chapter2.model.Customer;
import org.smartSpring.chapter2.util.DatabaseHelper;
import org.smartSpring.chapter2.util.PropsUtil;
import org.xml.sax.ErrorHandler;
import sun.rmi.runtime.Log;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by ym on 2018/5/9.
 */
@Slf4j
public class CustomerService {



    public List<Customer> getCustomerList() throws SQLException {

        String sql = "select * from customer";
        Connection connection = null;
        List<Customer> list = null;
        try {
            connection = DatabaseHelper.getConnect();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            list = Lists.newArrayList();
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getLong("id"));
                customer.setName(resultSet.getString("name"));
                customer.setContact(resultSet.getString("contact"));
                customer.setTelephone(resultSet.getString("telephone"));
                customer.setEmail(resultSet.getString("email"));
                customer.setRemark(resultSet.getString("remark"));
                list.add(customer);
            }
        } finally {
            DatabaseHelper.closeConnect(connection);
        }
        return list;
    }

    public List<Customer> getCustomerList2() {

        String sql = "select * from customer";
        Connection connection = null;
        List<Customer> list = null;
        try {
            connection = DatabaseHelper.getConnect();
            list = DatabaseHelper.getList(Customer.class, connection, sql);
        } catch (SQLException e) {
            log.error("execute sql failed", e);
        }
        return list;
    }

    public List<Customer> getCustomerList3() throws SQLException {
        String sql = "select * from customer";
        return DatabaseHelper.getList(Customer.class, sql);
    }

    public Customer getCustomer(Long id) {
        // TODO: 2018/5/9
        return null;
    }

    public boolean createCustomer(Map<String, Object> fieldMap) {
        // TODO: 2018/5/9
        return false;
    }

    public boolean updateCustomer(Long id, Map<String, Object> fieldMap) {
        // TODO: 2018/5/9
        return false;
    }

    public boolean deleteCustomer(Long id) {
        // TODO: 2018/5/9
        return false;
    }

}
