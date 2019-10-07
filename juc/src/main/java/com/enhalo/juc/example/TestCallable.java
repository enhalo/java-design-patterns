package com.enhalo.juc.example;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 一、创建执行线程的方式三： 实现Callable接口。相较于实现Runnable接口的方式，方法可以有返回值，并且可以抛出异常
 * 二、执行callable方式、需要FutureTask实现类的支持，用于接收运算结果。FutureTask是Future接口的实现类
 */
public class TestCallable {
    public static void main(String[] args) {
        ThreadCallableDemo td = new ThreadCallableDemo();
        // 1.执行Callable方式，需要FutureTask 实现类的支持，用于接收运算的结果
        FutureTask<Integer> result = new FutureTask(td);

        new Thread(result).start();


        // 2. 接收线程运算后的结果
        try{
            int sum = result.get();// FutureTask，可用于 闭锁
            System.out.println(sum);
            System.out.println("---------------------------");
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}


class ThreadCallableDemo implements Callable<Integer> {


    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for(int i =0; i <= 1000000; i++){
            sum +=i;
        }

        return sum;
    }
}
