package com.liuwu.entity.permission;

import lombok.Data;

import java.io.Serializable;

@Data
public class BackResources implements Serializable{
    private Long id;

    private String name;

    private Long parentId;

    private String resKey;

    private Byte type;

    private String resUrl;

    private Integer level;

    private String icon;

    private Boolean isHide;

    private String description;

}