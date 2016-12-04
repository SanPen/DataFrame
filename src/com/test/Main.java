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

        DataFrame data_frame = new CSV("Data.csv", ";").read();
        data_frame.ShowTable();

        data_frame.statistics_df().ShowTable();

    }
}
