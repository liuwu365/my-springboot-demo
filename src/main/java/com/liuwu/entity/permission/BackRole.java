package com.liuwu.entity.permission;

import lombok.Data;

import java.io.Serializable;

@Data
public class BackRole implements Serializable {
    private Long id;

    private Integer state;

    private String name;

    private String roleKey;

    private String description;

}