package com.liuwu.entity.permission;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BackGroup implements Serializable{
    private Long id;

    private Long deptId;

    private String groupName;

    private Date createTime;

    private Date updateTime;

}