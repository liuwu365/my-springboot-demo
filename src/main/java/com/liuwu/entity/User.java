package com.liuwu.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class User implements Serializable {
    private Integer id;

    private String name;

    private Integer age;

    private Boolean sex;

    private String address;

    private Date createTime;

    private String remark;


}