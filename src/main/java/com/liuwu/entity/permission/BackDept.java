package com.liuwu.entity.permission;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class BackDept implements Serializable{
    private Long id;

    private String deptName;

    private Date createTime;

    private Date updateTime;

    private List<BackGroup> groupList;

}