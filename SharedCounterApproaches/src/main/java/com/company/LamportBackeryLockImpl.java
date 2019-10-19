package com.company;


public class LamportBackeryLockImpl implements Lock {

    private final int thread_number;
    private final int[] ticket_queue;
    private final boolean[] choosing;


    public LamportBackeryLockImpl(int thread_number){
        this.thread_number = thread_number;
        this.ticket_queue = new int[thread_number];
        this.choosing = new boolean[thread_number];
        for (int i = 0;i < thread_number;i++){
            ticket_queue[i] = 0;
            choosing[i] = false;
        }

    }


    @Override
    public void lock() {
        //переводим номер потока в диапазон от 0 до thread_number
        int thread_id= (int) Thread.currentThread().getId() % thread_number;
        choosing[thread_id] = true;

        int max = 0;
        for (int i = 0;i<thread_number;i++){
            int current_thread_queue_place = ticket_queue[i];
            if (current_thread_queue_place > max){
                max = current_thread_queue_place;
            }
        }

        ticket_queue[thread_id] = max + 1;
        choosing[thread_id] = false;

        for (int i = 0;i < thread_number; ++i){
            if (thread_id != i){
                while(choosing[i]){
                    Thread.yield();
                }
                while ((ticket_queue[i] != 0) &&
                        ((ticket_queue[thread_id] > ticket_queue[i] ||
                                ticket_queue[thread_id] == ticket_queue[i] && (thread_id > i)))){
                    Thread.yield();
                }
            }
        }
    }

    @Override
    public void unlock() {
        int thread_id= (int) Thread.currentThread().getId() % thread_number;
        ticket_queue[thread_id] = 0;
    }
}
