package com.liuwu;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: java8新特性
 * @Date: 2018-03-23 10:28
 */
public class Java8Function {
    private static final Logger logger = LoggerFactory.getLogger(Java8Function.class);
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        //streamTest();
        method2();
    }


    /**
     * stream相关的测试
     */
    public static void streamTest() {
        List<String> filterLists = new ArrayList<>();
        filterLists.add("");
        filterLists.add("a");
        filterLists.add("b");
        filterLists.add("c");
        filterLists.add("d");
        filterLists.add("e");
        filterLists.add("f");

        long count = filterLists.stream().filter(s -> s != null && s != "").count();
        logger.info("不为空的个数：" + count);

        //排掉为空的
        List afterFilterLists = filterLists.stream()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
        logger.info(gson.toJson(afterFilterLists));


        //使用map操作可以遍历集合中的每个对象，并对其进行操作
        List<String> output = filterLists.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        logger.info(gson.toJson(output));

        //过滤
        //filterLists.removeIf(s -> s.contains("a"));
        //logger.info(gson.toJson(filterLists));
        //判断集合中是否满足条件(allMatch、anyMatch、noneMatch)
        boolean isExits = filterLists.stream().anyMatch(s -> s.equals("c"));
        logger.info(gson.toJson(isExits));


        //limit 返回 Stream 的前面 n 个元素；skip 则是扔掉前 n 个元素
        List<String> limitLists = filterLists.stream().skip(2).limit(3).collect(Collectors.toList());
        logger.info(gson.toJson(limitLists)); //bcd

        //排序（sort/min/max/distinct）
        List<Integer> sortLists = new ArrayList<>();
        sortLists.add(1);
        sortLists.add(4);
        sortLists.add(6);
        sortLists.add(3);
        sortLists.add(2);
        List<Integer> afterSortLists = sortLists.stream().sorted((In1, In2) -> In1 - In2).collect(Collectors.toList());
        logger.info(gson.toJson(afterSortLists));

    }

    public static void method2() {
        DefaultTest.staticMethod();


        DefaultTest defaultTest = (a, b) -> a - b;

        System.out.println(defaultTest.sub(6, 3));
    }

}
