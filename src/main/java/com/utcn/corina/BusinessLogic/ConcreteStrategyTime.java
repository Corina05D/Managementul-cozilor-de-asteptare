package com.utcn.corina.BusinessLogic;

import com.utcn.corina.Model.Server;
import com.utcn.corina.Model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class ConcreteStrategyTime implements Strategy {

    @Override
    public void addTask(ArrayList<Server> servers, Task task, ArrayList<Integer> newServers) {
        int minTimeIndex=Integer.MAX_VALUE;
        int minTime=Integer.MAX_VALUE;
        Task t=new Task(0,-1,0);
        for(Server server: servers){
            if(server.getWaitingPeriod().get()<minTime){
                minTimeIndex=servers.indexOf(server);
                minTime=server.getWaitingPeriod().get();
            }
        }
        if(!task.equals(t)) servers.get(minTimeIndex).addTask(task);
    }
}
