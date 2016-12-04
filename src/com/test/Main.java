package com.test;

import com.dataframe.CSV;
import com.dataframe.DataFrame;


public class Main {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        DataFrame data_frame = new CSV("Data.csv", ";").read();
        data_frame.showTable();

        data_frame.statistics_df().showTable();

    }
}
