package com.liuwu;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Description:
 * @Date: 2018-03-13 10:40
 */
public class OtherTest {
    public static final Gson gson = new Gson();

    public static void main(String[] args) {
        //methodB();
        InputStream in = OtherTest.class.getClassLoader().getResourceAsStream("my-config.properties");
        Properties properties = new Properties();
        try {
            properties.load(in);
            System.out.println(properties.get("myconfig.name"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 引用传递
     */
    public static void methodA() {
        List<List<String>> fatherList = new LinkedList<>();
        List<String> childList = new ArrayList<>();
        childList.add("before add child list");
        fatherList.add(childList);
        childList.add("after add child list");

        for (List<String> list : fatherList) {
            System.out.println(gson.toJson(list));
        }
    }

    public static void methodB() {
        ExecutorService es = Executors.newSingleThreadExecutor();
        CallableDemo calTask = new CallableDemo();
        Future<Integer> future = es.submit(calTask);
        es.shutdown();
        try {
            System.out.println("看看:"+future.get());
            Thread.sleep(2000);
            System.out.println("主线程在执行其他任务");

            if (future.get() != null) {
                //输出获取到的结果
                System.out.println("future.get()-->" + future.get());
            } else {
                //输出获取到的结果
                System.out.println("future.get()未获取到结果");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("主线程在执行完成");

    }

    static class CallableDemo implements Callable<Integer> {
        private int sum;

        @Override
        public Integer call() throws Exception {
            System.out.println("Callable子线程开始计算啦！");
            Thread.sleep(5000);

            for (int i = 0; i < 5000; i++) {
                sum = sum + i;
            }
            System.out.println("Callable子线程计算结束！");
            return sum;
        }
    }


}
