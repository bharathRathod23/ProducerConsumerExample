package com.bvr.multiThreading.concepts;


import java.nio.Buffer;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MessagingBuffer {


   // private final Lock lock = new ReentrantLock();


    private Queue<Integer> queue = new LinkedList<>();

    private int capacity;

    public MessagingBuffer(int capacity) {
        this.capacity = capacity;
    }


    public synchronized void produce(int value) throws InterruptedException {

        while(queue.size() == capacity) {
            System.out.println("producer thread is waiting");
            wait();
        }

        queue.add(value);
        System.out.println(Thread.currentThread().getName() + " Produced: "+  value);


        notifyAll();

    }


    public synchronized int consume() throws InterruptedException{

        while(queue.isEmpty()) {
            System.out.println("consumer thread is waiting");
            wait();
        }

        int value = queue.poll();
        System.out.println(Thread.currentThread().getName() + " Consumed: "+ value);
        notifyAll();
        return value;

    }

}


class Producer extends Thread {


    int value=0;

   private MessagingBuffer messagingBuffer;

    public Producer(MessagingBuffer messagingBuffer) {
        this.messagingBuffer = messagingBuffer;
    }

    @Override
    public void run() {


        try {
            while (true) {

                messagingBuffer.produce(value++);
                Thread.sleep(1000);
            }
        }
        catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }



    }

}



class Consumer extends Thread {

    private MessagingBuffer messagingBuffer;

    public Consumer(MessagingBuffer messagingBuffer) {
        this.messagingBuffer = messagingBuffer;
    }


    @Override
    public void run(){

        try {
            while (true) {

                messagingBuffer.consume();
                Thread.sleep(2000);
            }
        }
        catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }


    }


}

public class ProducerConsumerExample {


    public static void main(String[] args) {

        MessagingBuffer buffer = new MessagingBuffer(5);

        Producer producer1 = new Producer(buffer);
        Producer producer2 = new Producer(buffer);

        Consumer consumer = new Consumer(buffer);

        producer1.start();
        producer2.start();
        consumer.start();

    }


}
