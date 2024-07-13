package com.bvr.multiThreading.concepts;

public class ProducerConsumerExample2 {
    private static final Object lock = new Object();
    private static int count = 0;

    public static void main(String[] args) {
        Thread producer1 = new Producer("Producer 1");
        Thread producer2 = new Producer("Producer 2");
        Thread consumer = new Consumer();

        producer1.start();
        producer2.start();
        consumer.start();
    }

    static class Producer extends Thread {
        public Producer(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    count++;
                    System.out.println(Thread.currentThread().getName() + " produced: " + count);
                    lock.notify();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    static class Consumer extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    System.out.println("Consumed: " + count);
                    lock.notify();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
}
