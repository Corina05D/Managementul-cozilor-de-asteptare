package com.utcn.corina.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View {
    private JFrame frame;
    private JTextField tasks;
    private JTextField servers;
    private JTextField timeLimit;
    private JTextField arrivalTimeMin;
    private JTextField arrivalTimeMax;
    private JTextField serviceTimeMin;
    private JTextField serviceTimeMax;

    private Button start;
    private Button end;

    public View(){
        frame=new JFrame("Date de simulare");
        frame.getContentPane().setBackground(Color.YELLOW);
        JLabel lTasks=new JLabel("Numar de task-uri:");
        lTasks.setBounds(10,50,150,30);
        frame.add(lTasks);
        tasks=new JTextField("");
        tasks.setBounds(150,50,150,30);
        tasks.setBackground(Color.orange);
        frame.add(tasks);

        JLabel lServers=new JLabel("Numar de servere:");
        lServers.setBounds(10,100,150,30);
        frame.add(lServers);
        servers=new JTextField("");
        servers.setBounds(150,100,150,30);
        servers.setBackground(Color.orange);
        frame.add(servers);

        JLabel lTimeLimit=new JLabel("Cat dureaza simularea:");
        lTimeLimit.setBounds(10,150,150,30);
        frame.add(lTimeLimit);
        timeLimit=new JTextField("");
        timeLimit.setBounds(150,150,150,30);
        timeLimit.setBackground(Color.orange);
        frame.add(timeLimit);

        JLabel service=new JLabel("Servire:");
        service.setBounds(10,200,70,30);
        frame.add(service);
        serviceTimeMin=new JTextField("");
        serviceTimeMin.setBounds(75,200,150,30);
        serviceTimeMin.setBackground(Color.orange);
        frame.add(serviceTimeMin);

        serviceTimeMax=new JTextField("");
        serviceTimeMax.setBounds(240,200,150,30);
        serviceTimeMax.setBackground(Color.orange);
        frame.add(serviceTimeMax);

        JLabel arrival=new JLabel("Sosire:");
        arrival.setBounds(10,250,70,30);
        frame.add(arrival);
        arrivalTimeMin=new JTextField("");
        arrivalTimeMin.setBounds(75,250,150,30);
        arrivalTimeMin.setBackground(Color.orange);
        frame.add(arrivalTimeMin);

        arrivalTimeMax=new JTextField("");
        arrivalTimeMax.setBounds(240,250,150,30);
        arrivalTimeMax.setBackground(Color.orange);
        frame.add(arrivalTimeMax);

        start=new Button("Start");
        start.setBounds(100,300,70,30);
        start.setBackground(Color.magenta);
        frame.add(start);

        end=new Button("Iesire");
        end.setBounds(250,300,70,30);
        end.setBackground(Color.magenta);
        frame.add(end);

        frame.setSize(500,450);
        frame.setLayout(null);
        frame.setVisible(true);

    }

    public int getNumberOfTasks(){
        if(tasks.getText().matches("[0-9]*")) return Integer.parseInt(tasks.getText());
        else{
            JOptionPane.showMessageDialog(null,"Ati introdus un numar de task-uri invalid!");
            return -1;
        }
    }

    public int getNumberOfServers(){
        if(servers.getText().matches("[0-9]*")) return Integer.parseInt(servers.getText());
        else{
            JOptionPane.showMessageDialog(null,"Ati introdus un numar de servere invalid!");
            return -1;
        }
    }

    public int getTimeLimit(){
        if(timeLimit.getText().matches("[0-9]*")) return Integer.parseInt(timeLimit.getText());
        else{
            JOptionPane.showMessageDialog(null,"Ati introdus un numar de simulare invalid!");
            return -1;
        }
    }

    public int getArrivalTimeMin(){
        if(arrivalTimeMin.getText().matches("[0-9]*")) return Integer.parseInt(arrivalTimeMin.getText());
        else{
            JOptionPane.showMessageDialog(null,"Ati introdus un numar de sosire minim invalid!");
            return -1;
        }
    }

    public int getArrivalTimeMax(){
        if(arrivalTimeMax.getText().matches("[0-9]*")) return Integer.parseInt(arrivalTimeMax.getText());
        else{
            JOptionPane.showMessageDialog(null,"Ati introdus un numar de sosire maxim invalid!");
            return -1;
        }
    }

    public int getServiceTimeMin(){
        if(serviceTimeMin.getText().matches("[0-9]*")) return Integer.parseInt(serviceTimeMin.getText());
        else{
            JOptionPane.showMessageDialog(null,"Ati introdus un numar de servire minim invalid!");
            return -1;
        }
    }

    public int getServiceTimeMax(){
        if(serviceTimeMax.getText().matches("[0-9]*")) return Integer.parseInt(serviceTimeMax.getText());
        else{
            JOptionPane.showMessageDialog(null,"Ati introdus un numar de servire maxim invalid!");
            return -1;
        }
    }

    public void startActionListener(ActionListener a){
        start.addActionListener(a);
    }

    public void endtActionListener(ActionListener a){
        end.addActionListener(a);
    }
    public void close(){
        frame.dispose();
    }

}
