package com.utcn.corina.GUI;

import com.utcn.corina.BusinessLogic.SimulationManager;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Controller {
    private View v;

    public Controller(View v){
        this.v=v;
        v.startActionListener(new startActionListener());
        v.endtActionListener(new endActionListener());
    }
    public class startActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SimulationManager gen = new SimulationManager("fisierIesire.txt",v.getNumberOfServers(),v.getNumberOfTasks(),v.getTimeLimit(),v.getServiceTimeMin(),v.getServiceTimeMax(),v.getArrivalTimeMin(),v.getArrivalTimeMax());
            if (v.getServiceTimeMax() < 1 || v.getArrivalTimeMax() < 1 || v.getArrivalTimeMin() < 1 || v.getServiceTimeMin() < 1)
                JOptionPane.showMessageDialog(null, "Date de intrare incorecte");
            else {
                try {
                    gen.outStream=new FileWriter(gen.outFile);
                    Thread t = new Thread(gen);
                    t.start();
                } catch (IOException ex) {
                    File newoutStream=new File(gen.outFile);
                    try {
                        if(newoutStream.createNewFile()) System.out.println("Fisier creat cu succes");
                    } catch (IOException exc) {
                        System.out.println("Eroare la crearea fisierului");
                    }
                }

            }
        }
    }
        public class endActionListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                v.close();
            }
        }
    }

