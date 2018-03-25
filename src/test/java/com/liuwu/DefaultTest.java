package com.liuwu;

/**
 * Created by Administrator on 2018/3/23.
 */
@FunctionalInterface
public interface DefaultTest {

    default void defaultMethod() {
        System.out.println("DefalutTest default 方法");
    }

    static void staticMethod() {
        System.out.println("DefalutTest static 方法");
    }

    int sub(int a, int b);
}
