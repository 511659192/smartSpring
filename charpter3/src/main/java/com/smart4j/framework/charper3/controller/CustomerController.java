package com.smart4j.framework.charper3.controller;

import com.google.common.collect.Lists;
import com.smart4j.framework.annotation.Action;
import com.smart4j.framework.annotation.Controller;
import com.smart4j.framework.annotation.Inject;
import com.smart4j.framework.bean.Data;
import com.smart4j.framework.bean.Param;
import com.smart4j.framework.charper3.model.Customer;
import com.smart4j.framework.charper3.service.CustomerService;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ym on 2018/5/11.
 */
@Controller
public class CustomerController {

    @Inject
    private CustomerService customerService;

    @Action(value = "GET:/list")
    public Data getList(Param param) {
        List<Customer> customerList = Lists.newArrayList();
        try {
            customerList.addAll(customerService.getCustomerList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Data data = new Data(customerList.get(0));
        return data;
    }

    @Action(value = "GET:/update")
    public Data update(Param param) {
        Integer update = 0;
        try {
            update = customerService.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Data data = new Data(update);
        return data;
    }
}
