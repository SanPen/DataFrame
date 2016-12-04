package com.dataframe;


public class Stats2D {

    int columns;

    Object[][] data; //col, row

    /**
     * Mean value per column
     */
    public double[] mean;

    /**
     * Standard deviation per column
     */
    public double[] std;

    /**
     * maximum value per column
     */
    public double[] max;

    /**
     * minimum value per column
     */
    public double[] min;

    /**
     * Summation per column
     */
    public double[] sum;


    /**
     * @param array: 2D double array (columns, rows)
     */
    public Stats2D(Object[][] array) {
        data = array;
        columns = data.length;
    }


    /**
     * Executes the statistics for the provided data
     * considering each column as a data series.
     */
    public void run() {

        mean = new double[columns];
        std = new double[columns];
        max = new double[columns];
        min = new double[columns];
        sum = new double[columns];

        for (int col = 0; col < columns; col++) {
            Stats1D stats1d = new Stats1D(data[col]);
            stats1d.run();

            mean[col] = stats1d.mean;
            std[col] = stats1d.std;
            max[col] = stats1d.max;
            min[col] = stats1d.min;
            sum[col] = stats1d.sum;
        }
    }


    public String toString() {
        String s = "";
        s += "Mean \t\t\t Std \t\t\t Max \t\t\t Min \t\t\t Sum \n";
        for (int col = 0; col < columns; col++) {
            s += mean[col] + "\t " + std[col] + " \t " + max[col] + " \t " + min[col] + " \t " + sum[col] + " \n";
        }
        return s;
    }

}
