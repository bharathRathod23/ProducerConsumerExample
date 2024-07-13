package com.bvr.multiThreading.concepts;

public class MyThread  extends Thread {


    @Override
    public void run(){

        System.out.println("Thread is running");
    }


    public static void main(String[] args) {


        MyThread thread = new MyThread();
        thread.start();
    }



}
