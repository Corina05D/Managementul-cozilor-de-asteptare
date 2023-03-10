package com.utcn.corina.BusinessLogic;

import com.utcn.corina.Model.Server;
import com.utcn.corina.Model.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationManager implements Runnable {
    private int numberOfServers ;
    private int numberOfTasks ;
    private int timeLimit ;
    private int minServiceTime ;
    private int maxServiceTime ;
    private int minArrivalTime ;
    private int maxArrivalTime ;

    public String outFile;
    public FileWriter outStream;

    private Scheduler scheduler;
    private BlockingQueue<Task> waitingTasks;
    private AtomicInteger currentTime;


    public SimulationManager(String file,int numberOfServers,int numberOfTasks,int timeLimit,int minServiceTime,int maxServiceTime,int minArrivalTime,int maxArrivalTime) {
        this.numberOfServers=numberOfServers;
        this.numberOfTasks=numberOfTasks;
        this.timeLimit=timeLimit;
        this.minServiceTime=minServiceTime;
        this.maxServiceTime=maxServiceTime;
        this.minArrivalTime=minArrivalTime;
        this.maxArrivalTime=maxArrivalTime;
        this.waitingTasks = new LinkedBlockingDeque<Task>();
        this.currentTime = new AtomicInteger(0);
        this.outFile = file;
        this.waitingTasks = this.generateNRandomTasks();
        this.scheduler = new Scheduler(this.numberOfServers);
    }

    public BlockingQueue<Task> generateNRandomTasks() {
        int number1, number2;
        ArrayList<Task> generatedTasks = new ArrayList<Task>();
        LinkedBlockingQueue<Task> taskServer = new LinkedBlockingQueue<Task>();
        for (int i = 0; i < numberOfTasks; i++) {
            Random random = new Random();
            number1 = random.nextInt(maxArrivalTime);
            while (number1 < minArrivalTime) {
                number1 = random.nextInt(maxArrivalTime);
            }
            number2 = random.nextInt(maxServiceTime);
            while (number2 < minServiceTime) {
                number2 = random.nextInt(maxServiceTime);
            }
            generatedTasks.add(new Task(i + 1, number1, number2));
        }
        generatedTasks.sort(Task.ArrivalTimeComparator);
        for (Task task : generatedTasks) {
            task.setID(generatedTasks.indexOf(task) + 1);
            taskServer.add(task);
        }
        generatedTasks.add((new Task(0, -1, 0)));
        return taskServer;
    }

    public void run() {
        double r=averageServingTime();
        while (currentTime.get() <= timeLimit) {
            try {
                if (checkUnexpectedStop() == 1) break;
                for (Task task : waitingTasks) {
                    if (task.getServiceTime() + scheduler.getTimeCounter().get() < timeLimit - currentTime.get() - 1 && task.getArrivalTime() == currentTime.get() || task.equals(new Task(0, -1, 0))) {
                        scheduler.dispatchTask(task);
                        if (!task.equals(new Task(0, -1, 0)))
                            waitingTasks.poll();
                    }
                }
                for (Server server : scheduler.getServers()) {
                    server.processThread();
                }
                writeInFile();
                currentTime.getAndIncrement();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        try {
            if (!waitingTasks.isEmpty())
                outStream.append("Din pacate, mai sunt task-uri care nu au putut fi procesate in aceasta simulare!\n");
            if (checkUnexpectedStop() == 1)
                outStream.append("Simularea s-a terminat mai devreme. Nu mai sunt task-uri de procesat.");
            outStream.append("\nTimp mediu de asteptare: ").append(String.valueOf(averageWaitingTime())).append("\n");
            outStream.append("\nTimp mediu de servire: ").append(String.valueOf(r)).append("\n");
            outStream.close();
        } catch (IOException e) {
            System.out.println("Eroare 2 la scrierea in fisier!");
            e.printStackTrace();
        }
    }

    public void writeInFile() {
        try {
            outStream.append("Time: ").append(String.valueOf(currentTime)).append("\nTask-uri in asteptare:: ");
            for (Task task : waitingTasks) {
                outStream.append("(").append(String.valueOf(task.getID())).append(",").append(String.valueOf(task.getArrivalTime())).append(",").append(String.valueOf(task.getServiceTime())).append("); ");
            }
            outStream.append("\n");
            for (Server server : scheduler.getServers()) {
                if (server.getTasks().isEmpty())
                    outStream.append("Server ").append(String.valueOf(server.getServerNumber())).append(": inchis\n");
                else {
                    outStream.append("Server ").append(String.valueOf(server.getServerNumber())).append(":");
                    for (Task task : server.getTasks()) {
                        if (!task.equals(new Task(0, 0, 0)))
                            outStream.append(" (").append(String.valueOf(task.getID())).append(",").append(String.valueOf(task.getArrivalTime())).append(",").append(String.valueOf(task.getServiceTime())).append("); ");
                    }
                    outStream.append("\n");
                }
            }
            outStream.append("\n");
        } catch (IOException e) {
            System.out.println("Eroare la scrierea in fisier!");
            e.printStackTrace();
        }

    }


    public double averageWaitingTime() {
        double aTime = 0;
        for (Server server : scheduler.getServers()) {
            aTime = aTime + server.getAverageWaitingTime();
        }
        return aTime / scheduler.getServers().size();
    }

    public double averageServingTime(){
        double aTime=0;
        for(Task task:waitingTasks){
            aTime=aTime+task.getServiceTime();
        }
        return aTime/waitingTasks.size();
    }

    public int checkUnexpectedStop() {
        if (waitingTasks.isEmpty() || waitingTasks.peek().equals(new Task(0, 0, 0))) {
            int t = 0;
            for (Server server : scheduler.getServers())
                if (server.getTasks().isEmpty()) t++;
            if (t == numberOfServers) return 1;
        }
        return 0;
    }

}
