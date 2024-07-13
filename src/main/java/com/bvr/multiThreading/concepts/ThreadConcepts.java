package com.bvr.multiThreading.concepts;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadConcepts {

//In Java 8 and later, you can use lambda expressions to simplify the creation of threads.


    public static void main(String[] args) {


        Runnable runnable = () -> {

            System.out.println(Thread.currentThread()+ " thread is running");

        };

        Thread thread = new Thread(runnable);
        thread.start();


        //The Executors framework provides a higher-level API for managing a pool of threads

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Runnable task1 = () -> {
            System.out.println(Thread.currentThread() + " Task 1 is running");
        };

        Runnable task2 = () -> {
            System.out.println(Thread.currentThread() + " Task 2 is running");
        };

        Runnable task3 = () -> {
            System.out.println(Thread.currentThread() + " Task 3 is running");
        };


        executorService.execute(task1);
        executorService.execute(task2);
        executorService.execute(task3);

        executorService.shutdown();  // Initiates an orderly shutdown of the executor


    }
}
