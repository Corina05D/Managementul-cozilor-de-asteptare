package com.utcn.corina.Model;

import com.utcn.corina.BusinessLogic.SimulationManager;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private int serverNumber;
    private int averageWaitingTime;
    private int numberOfTasks;

    public Server(int serverNumber) {
        tasks= new LinkedBlockingQueue<>();
        waitingPeriod=new AtomicInteger(0);
        this.numberOfTasks=0;
        this.averageWaitingTime=0;
        this.serverNumber=serverNumber;
    }

    public void addTask(Task task) {
        waitingPeriod.getAndAdd(task.getServiceTime());
        if(tasks.isEmpty()){
            task.incrementWaitingTime();
            task.setFirst();
        }
        numberOfTasks++;
        tasks.add(task);

    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public int getServerNumber(){
        return serverNumber;
    }

    public double getAverageWaitingTime(){
        return  (double)averageWaitingTime/numberOfTasks;
    }

    public void processThread(){
        if(!tasks.isEmpty()){
            averageWaitingTime=averageWaitingTime+tasks.size();
            if(tasks.peek().getServiceTime()==1) this.getTasks().poll();
            if(!tasks.isEmpty()){
                if(tasks.peek().isFirst()) tasks.peek().decrementWaitingTime();
                tasks.peek().setFirst();
                waitingPeriod.getAndDecrement();
            }
            else waitingPeriod.set(0);
        }
        else waitingPeriod.set(0);
    }

    @Override
    public void run() {
        if(!tasks.isEmpty()){
            if(tasks.peek().getServiceTime()==0) this.getTasks().poll();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
