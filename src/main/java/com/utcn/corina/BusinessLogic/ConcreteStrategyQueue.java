package com.utcn.corina.BusinessLogic;

import com.utcn.corina.Model.Server;
import com.utcn.corina.Model.Task;

import java.util.ArrayList;
import java.util.List;

public class ConcreteStrategyQueue implements Strategy{
    @Override
    public void addTask(ArrayList<Server> servers, Task task, ArrayList<Integer> newServers) {
        Task t=new Task(0,-1,0);
        if(!task.equals(t)) servers.get(newServers.get(0)).addTask(task);
    }
}
