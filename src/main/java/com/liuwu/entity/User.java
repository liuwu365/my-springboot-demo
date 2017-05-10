package com.liuwu.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class User implements Serializable {
    @Excel(name = "ID")
    private int id;
    @Excel(name = "姓名")
    private String name;
    @Excel(name = "年龄")
    private int age;
    @Excel(name = "性别", replace = {"男_1", "女_2"}, suffix = "生", isImportField = "true")
    private int sex;
    @Excel(name = "地址")
    private String address;
    @Excel(name = "创建时间", format = "yyyy-MM-dd HH:mm:ss",width = 30)
    private Date createTime;
    @Excel(name = "备注")
    private String remark;


}