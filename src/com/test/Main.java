package com.test;

import com.dataframe.CSV;
import com.dataframe.DataFrame;
import com.dataframe.Stats2D;


public class Main {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        ui gui = new ui();

        DataFrame data_frame = new CSV("Data.csv", ";").read();
        gui.setModel(data_frame);

//        data_frame.Resample(15,"minute");

        Stats2D res = data_frame.statistics();
        System.out.println(res);

        System.out.println(data_frame.data.sum(0));
    }
}
