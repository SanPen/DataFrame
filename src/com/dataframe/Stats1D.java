package com.dataframe;


public class Stats1D {

    Object[] data;

    public double mean;

    public double std;

    public double max;

    public double min;

    public double sum;


    public Stats1D(Object[] array) {
        data = array;
    }

    public void run() {

        int n = data.length;

        double summation = 0.0;
        double d;
        mean = 0;
        std = 0;
        max = 0;
        min = 1e20;

        for (int i = 0; i < n; i++) {
            d = (double) data[i];
            summation += d;

            if (d > max)
                max = d;

            if (d < min)
                min = d;
        }

        sum = summation;
        mean = summation / n;

        summation = 0;
        for (int i = 0; i < n; i++) {
            d = ((double) data[i])  - mean;
            summation += d*d ;
        }
        std = Math.sqrt(summation/n);

    }

}
