package com.liuwu.entity;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Date: 2017/9/7.
 */
@Data
@Accessors(chain = true)
@ExcelTarget("PrizeBonus")
public class PrizeBonus implements Serializable {
    @Excel(name = "ID")
    private Long id;
    @Excel(name = "状态")
    private Integer status;
    private Long operator;
    @Excel(name = "发放人")
    @NotNull
    private String operatorName;
    private Long target;
    @Excel(name = "目标用户")
    private String targetName;
    private Long proxyUid;
    @Excel(name = "代理用户")
    private String proxyName;
    @Excel(name = "奖金")
    private BigDecimal prize;
    @Excel(name = "系统红利")
    private BigDecimal sysBonus;
    @Excel(name = "代理红利")
    private BigDecimal proxyBonus;
    @Excel(name = "系统红利发放时间")
    private Date grantTime;
    @Excel(name = "代理红利发放时间")
    private Date proxyGrantTime;

    private Date beginTime;
    private Date endTime;
    private Integer type;
}
