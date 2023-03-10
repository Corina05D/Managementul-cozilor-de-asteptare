package com.utcn.corina.BusinessLogic;

import com.utcn.corina.GUI.Controller;
import com.utcn.corina.GUI.View;

public class Main {
    public static void main(String[] args) {
        View v = new View();
        Controller c = new Controller(v);
    }
}
