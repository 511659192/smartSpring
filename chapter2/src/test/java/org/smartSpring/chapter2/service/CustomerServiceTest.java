package org.smartSpring.chapter2.service;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.smartSpring.chapter2.model.Customer;

import java.util.List;

/**
 * Created by ym on 2018/5/9.
 */
public class CustomerServiceTest {
    @Test
    public void getCustomerList3() throws Exception {
        List<Customer> customerList = customerService.getCustomerList();
        System.out.println(JSON.toJSONString(customerList));
    }

    @Test
    public void getCustomerList2() throws Exception {
        List<Customer> customerList = customerService.getCustomerList();
        System.out.println(JSON.toJSONString(customerList));
    }

    private CustomerService customerService;

    public CustomerServiceTest() {
        this.customerService = new CustomerService();
    }

    @Before
    public void init() {
        // TODO: 2018/5/9
    }

    @Test
    public void getCustomerList() throws Exception {

        List<Customer> customerList = customerService.getCustomerList();
        System.out.println(JSON.toJSONString(customerList));
    }

    @Test
    public void getCustomer() throws Exception {
    }

    @Test
    public void createCustomer() throws Exception {
    }

    @Test
    public void updateCustomer() throws Exception {
    }

    @Test
    public void deleteCustomer() throws Exception {
    }

}
