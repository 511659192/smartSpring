package org.smartSpring.chapter2.model;

import lombok.Data;

/**
 * Created by ym on 2018/5/9.
 */

@Data
public class Customer {

    private Long id;

    private String name;

    private String contact;

    private String telephone;

    private String email;

    private String remark;
}
