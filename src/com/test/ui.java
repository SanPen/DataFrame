package com.test;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by santi on 3/12/16.
 */
public class ui {
    private JTable table1;
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton doButton;


    /**
     * Class constructor
     */
    public ui(){
        JFrame frame = new JFrame("App");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800, 500);
        System.out.println("UI created");


        doButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Do!" + textField1.getText());
            }
        });
    }

    public void setModel(AbstractTableModel mdl){

        table1.setModel(mdl);
    }

}
