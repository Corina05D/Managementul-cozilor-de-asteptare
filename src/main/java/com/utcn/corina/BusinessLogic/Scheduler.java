package com.utcn.corina.BusinessLogic;

import com.utcn.corina.Model.Server;
import com.utcn.corina.Model.Task;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Scheduler {
    private ArrayList<Server> servers;
    private  ArrayList<Thread> threads;
    private AtomicInteger timeCounter;
    private Strategy strategy;

    public Scheduler(int numberOfServers) {
        servers=new ArrayList<Server>();
        threads=new ArrayList<Thread>();
        timeCounter=new AtomicInteger(0);
        changePolicy(StrategyPolicy.SHORTEST_TIME);
        for (int i=0;i<numberOfServers;i++) {
            servers.add(new Server(i+1));
            threads.add(new Thread(servers.get(i)));
            threads.get(i).start();;
        }
    }

    public void changePolicy(StrategyPolicy policy) {
        switch (policy) {
            case SHORTEST_TIME:
                this.strategy = new ConcreteStrategyTime();
                break;
            case SHORTEST_QUEUE:
                this.strategy = new ConcreteStrategyQueue();
                break;
        }
    }

    public void dispatchTask(Task task) {
        ArrayList<Integer> minServers=new ArrayList<Integer>();
        int minWTime=Integer.MAX_VALUE;
        int minWTIndex=-1;

        for(Server server: servers){
            if(server.getTasks().size()==1 && server.getTasks().peek().getServiceTime()==1){
                minWTIndex=server.getServerNumber()-1;
                break;
            }
            else if(server.getWaitingPeriod().get()<minWTime){
                    minWTime=server.getWaitingPeriod().get();
                    minWTIndex=server.getServerNumber()-1;
                }
        }
        for(Server server: servers){
            if(server.getTasks().isEmpty()){
                minWTIndex=server.getServerNumber()-1;
                break;
            }
        }
        timeCounter.set(servers.get(minWTIndex).getWaitingPeriod().get());
        minServers.add(minWTIndex);
        strategy.addTask(servers,task,minServers);
    }

    public List<Server> getServers() {

        return servers;
    }

    public AtomicInteger getTimeCounter(){
        return timeCounter;
    }
}
