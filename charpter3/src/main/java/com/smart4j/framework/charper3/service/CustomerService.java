package com.smart4j.framework.charper3.service;

import com.smart4j.framework.annotation.Service;
import com.smart4j.framework.annotation.Transaction;
import com.smart4j.framework.charper3.model.Customer;
import com.smart4j.framework.helper.DatabaseHelper;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ym on 2018/5/9.
 */
@Slf4j
@Service
public class CustomerService {

    public List<Customer> getCustomerList() throws SQLException {
        String sql = "select * from customer";
        return DatabaseHelper.getList(Customer.class, sql);
    }

    @Transaction
    public int update() throws SQLException {
        String sql = "update customer set name = 'updated' where id = 1";
        return DatabaseHelper.update(sql);
    }

}
