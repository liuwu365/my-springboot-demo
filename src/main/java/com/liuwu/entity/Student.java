package com.liuwu.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 学生类
 * @User: liuwu_eva@163.com
 * @Date: 2017-09-11 17:27
 */
@Data
public class Student implements Serializable {
    private String name;

    private int age;

    private List<String> hobbys = new ArrayList<>();


    public Student(String name, int age, List<String> hobbys) {
        this.name = name;
        this.age = age;
        this.hobbys = hobbys;
    }
}