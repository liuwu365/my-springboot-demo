package com.liuwu.entity.permission;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class BackUser implements Serializable{
    private Long id;

    private String userName;

    private String accountName;

    private String password;

    private String credentialsSalt;

    private String description;

    private Boolean locked;

    private Date createTime;

    /*辅助字段*/
    private String sessionId;

    private String roleName;

    private List<BackUserRoleKey> roleList;

}