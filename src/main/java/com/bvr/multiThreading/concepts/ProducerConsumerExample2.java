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





//import java.util.LinkedList;
//import java.util.Queue;
//
//class Buffer {
//    private final Queue<Integer> queue = new LinkedList<>();
//    private final int capacity;
//    private final Object lock = new Object();
//
//    public Buffer(int capacity) {
//        this.capacity = capacity;
//    }
//
//    public void produce(int value) throws InterruptedException {
//        synchronized (lock) {
//            while (queue.size() == capacity) {
//                lock.wait();
//            }
//            queue.add(value);
//            System.out.println("Produced: " + value);
//            lock.notifyAll();
//        }
//    }
//
//    public int consume() throws InterruptedException {
//        synchronized (lock) {
//            while (queue.isEmpty()) {
//                lock.wait();
//            }
//            int value = queue.poll();
//            System.out.println("Consumed: " + value);
//            lock.notifyAll();
//            return value;
//        }
//    }
//}
//
//class Producer extends Thread {
//    private final Buffer buffer;
//
//    public Producer(Buffer buffer) {
//        this.buffer = buffer;
//    }
//
//    @Override
//    public void run() {
//        int value = 0;
//        try {
//            while (true) {
//                buffer.produce(value++);
//                Thread.sleep(100);
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//}
//
//class Consumer extends Thread {
//    private final Buffer buffer;
//
//    public Consumer(Buffer buffer) {
//        this.buffer = buffer;
//    }
//
//    @Override
//    public void run() {
//        try {
//            while (true) {
//                buffer.consume();
//                Thread.sleep(100);
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//}
//
//public class ProducerConsumerExample {
//    public static void main(String[] args) {
//        Buffer buffer = new Buffer(5);
//
//        Thread producerThread1 = new Producer(buffer);
//        Thread producerThread2 = new Producer(buffer);
//        Thread consumerThread = new Consumer(buffer);
//
//        producerThread1.start();
//        producerThread2.start();
//        consumerThread.start();
//    }
//}
