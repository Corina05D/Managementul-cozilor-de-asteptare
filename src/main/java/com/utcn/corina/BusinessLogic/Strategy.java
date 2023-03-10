package com.utcn.corina.BusinessLogic;

import com.utcn.corina.Model.Server;
import com.utcn.corina.Model.Task;

import java.util.ArrayList;
import java.util.List;

public interface Strategy {
    void addTask(ArrayList<Server> servers, Task task,ArrayList<Integer> newServers);
}

