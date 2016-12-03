package com.test;

import com.dataframe.CSV;
import com.dataframe.DataFrame;


public class Main {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        ui gui = new ui();

        DataFrame data_frame = new CSV("Data.csv", ";").read();
        gui.setModel(data_frame);

        System.out.println(data_frame.data.sum(0));
    }
}
