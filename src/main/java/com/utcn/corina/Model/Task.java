package com.utcn.corina.Model;

import java.util.Comparator;

public class Task {
    private int id;
    private int arrivalTime;
    private int serviceTime;
    private int endTime;
    private boolean first;

    public Task(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.endTime = arrivalTime + serviceTime;
        this.first = false;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void incrementWaitingTime() {
        this.serviceTime++;
    }

    public void decrementWaitingTime() {
        this.serviceTime--;
    }

    public void setFirst() {
        first = true;
    }

    public static Comparator<Task> ArrivalTimeComparator = new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            if (o1.getArrivalTime() < o2.getArrivalTime()) {
                return -1;
            } else if (o1.getArrivalTime() > o2.getArrivalTime()) return 1;
            else return 0;
        }
    };

    public boolean isFirst() {
        return first;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
}
