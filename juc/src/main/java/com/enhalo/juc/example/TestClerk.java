package com.enhalo.juc.example;

public class TestClerk {


    public static void main(String[] args) {
        Clerk clerk =new Clerk();
        Product product = new Product(clerk);
        Consumer consumer = new Consumer(clerk);
        new Thread(product, "生产者A").start();
        new Thread(consumer, "消费者B").start();
        new Thread(product, "生产者C").start();
        new Thread(consumer, "消费者D").start();



    }



}

class Clerk{
    private int product ;

    public synchronized void get(){
       while(product >=1) {
           System.out.println("产品已满");
           try {
               this.wait();// 虚假唤醒，使用在while中
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }

           System.out.println(Thread.currentThread().getName() + ":" + ++product);

           this.notifyAll();

    }

    public synchronized void sale()  {
        while(product <= 0){
            System.out.println("缺货");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            System.out.println(Thread.currentThread().getName() + ":" + --product);
            this.notifyAll();

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

class Consumer implements Runnable{
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i =0; i<20; i++){
            clerk.sale();
        }

    }
}


