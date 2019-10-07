package com.enhalo.juc.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestProductAndConsumerForLock {


class Clerk{
    private int product ;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void get(){
        lock.lock();
        try{
            while(product >=1) {
                System.out.println("产品已满");
                try {
                    condition.await();// 虚假唤醒，使用在while中
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread().getName() + ":" + ++product);

           condition.signalAll();
        }finally {
            lock.unlock();
        }


    }

    public void sale()  {
        lock.unlock();
        try{
            while(product <= 0){
                System.out.println("缺货");
                try {
                   condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + ":" + --product);
            condition.signalAll();
        }finally {
            lock.unlock();
        }


    }
}

class Product implements Runnable{

    private Clerk clerk;

    public Product(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for(int i=0; i <20; i++){
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.get();
        }
    }
}

class Consumer implements Runnable {
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }

    }
}
}
