package com.dataframe;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.awt.BorderLayout;

import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;

import java.util.Arrays;

public class DataFrame extends AbstractTableModel {

    public String name = "";
    public Data data = null;

    public Index index = null;

    public Header header = null;

    /**
     * Class constructor
     *
     * @param data_    Data instance
     * @param columns_ Header instance
     * @param index_   Index instance
     */
    public DataFrame(Data data_, Header columns_, Index index_, String name_) {

        this.data = data_;

        this.header = columns_;

        this.index = index_;

        this.name = name_;

        if (data.rows != index.values.length) {
            throw new IllegalArgumentException("Data rows different from index rows");
        }
    }

    /**
     * Class constructor
     *
     * @param data    2D array (number of rows, number of columns)
     * @param columns
     * @param index
     */
    public DataFrame(Object[][] data, String[] columns, Object[] index, String name_) {
        this(new Data(data), new Header(columns), new Index(index), name_);
    }

    public DataFrame(Object[][] data, String[] columns, Object[] index) {
        this(new Data(data), new Header(columns), new Index(index), "DataFrame");
    }


    /**
     * Return a copy of the DataFrame
     *
     * @return Returns a copy of this DataFrame
     */
    public DataFrame copy() {
        return new DataFrame(data.values, header.values, index.values);
    }


    /**
     * Normalized cardinal sine function
     *
     * @param x
     * @return
     */
    private double sinc(double x) {
        return Math.sin(Math.PI * x) / (Math.PI * x);
    }


    /**
     * Resample of the data in the DataFrame
     * As seen at:
     * http://dsp.stackexchange.com/questions/8488/what-is-an-algorithm-to-re-sample-from-a-variable-rate-to-a-fixed-rate
     */
    public void Resample(double value, String units) {

        // dictionary with number of seconds per sampling unit
        Map<String, Double> units_dict = new HashMap<>();
        units_dict.put("week", 7.0 * 24 * 60 * 60 * 60);
        units_dict.put("day", 1.0 * 24 * 60 * 60 * 60);
        units_dict.put("hour", 1.0 * 60 * 60);
        units_dict.put("minute", 60.0);
        units_dict.put("second", 1.0);

        int m = index.values.length;
        Double[] xnow = index.convert_to_seconds();  // get the index times in seconds to be able to resample

        // compute the new times array
        Double span = units_dict.get(units) * value;  // get the selected time interval to resample
        int n = (int) Math.floor((xnow[m - 1] - xnow[0]) / span); //number of intervals
        Double[] xnew = new Double[n]; // new array of times
        Date[] new_dates = new Date[n];
        xnew[0] = Math.floor(xnow[0] / span) * span;
        for (int j = 1; j < n; j++) {
            xnew[j] = xnew[j - 1] + span;
            new_dates[j] = new Date((long) (xnew[j] * 1000));
        }
        // create the coefficients matrix A
        double T = 1.0 / n;  // period
        Double[][] A = new Double[m][n];

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                A[i][j] = sinc((xnow[i] - xnew[j]) / T);

        Double[] ynew = new Double[n];
    }

    /**
     * get data column array
     *
     * @param c
     * @return
     */
    public Object[] getColumn(int c) {
//        Object[] dta = new Object[data.rows];
//        for (int i = 0; i < data.rows; i++)
//            dta[i] = data.values[c][c];
        return data.values[c];
    }


    /**
     * Get data row array
     *
     * @param r
     * @return
     */
    public Object[] getRow(int r) {
        Object[] dta = new Object[data.columns];
        for (int i = 0; i < data.columns; i++)
            dta[i] = data.values[i][r];
        return dta;
    }

    /**
     * Returns a Subset DataFrame
     *
     * @param c: array of column indices
     * @param r: array of row indices
     * @return new subset DataFrame
     */
    public DataFrame getSubset(int[] c, int[] r) {

        Object[][] data = new Object[r.length][c.length];
        Object[] idx = new Object[r.length];
        String[] hdr = new String[c.length];

        int rr = 0;
        int cc;
        for (int row : r) {
            cc = 0;
            for (int col : c) {
                data[rr][cc] = this.data.values[col][row];
                idx[rr] = this.index.values[row];
                hdr[cc] = this.header.values[col];
                cc++;
            }
            rr++;
        }

        return new DataFrame(data, hdr, idx, "SubSet of " + name);
    }

    /**
     * Return a subset of the DataFrame composed by the specified columns
     *
     * @param columns
     * @return
     */
    public DataFrame getSubset(String[] columns) {

        // get the indices matching the columns
        int[] c = this.header.getIndices(columns);

        Object[][] data = new Object[this.data.rows][c.length];
        Object[] idx = new Object[this.data.rows];
        String[] hdr = new String[c.length];

        int rr = 0;
        int cc;
        for (int row = 0; row < this.data.rows; row++) {
            cc = 0;
            for (int col : c) {
                data[rr][cc] = this.data.values[col][row];
                idx[rr] = this.index.values[row];
                hdr[cc] = this.header.values[col];
                cc++;
            }
            rr++;
        }

        return new DataFrame(data, hdr, idx, "SubSet of " + name);
    }

    /**
     * Return the descriptive statistics per column
     *
     * @return Stats2D instance
     */
    public Stats2D statistics() {
        Stats2D stats = new Stats2D(data.values);
        stats.run();
        return stats;
    }

    /**
     * @return
     */
    public DataFrame statistics_df() {
        Stats2D stats = new Stats2D(data.values);
        stats.run();

        Object[] idx = {"Mean", "Std", "Max", "Min", "Sum"};

        Object[][] vals = new Object[data.columns][5];

        for (int col = 0; col < stats.columns; col++) {
            vals[col][0] = stats.mean[col];
            vals[col][1] = stats.std[col];
            vals[col][2] = stats.max[col];
            vals[col][3] = stats.min[col];
            vals[col][4] = stats.sum[col];
        }

        return new DataFrame(vals, header.values, idx, "Statistics");
    }


    /**
     * Display this dataFrame in a Swing GUI
     */
    public void showTable() {
        JTable table = new JTable();
        table.setModel(this);
        JFrame frame = new JFrame(name);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800, 500);
    }


    public int getColumnCount() {
        return data.columns + 1;
    }

    public int getRowCount() {
        return index.values.length;
    }

    public Object getValueAt(int row, int col) {
        if (col == 0)
            return index.values[row];
        else
            return data.values[col - 1][row];
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 0)
            index.values[rowIndex] = aValue;
        else
            data.values[columnIndex - 1][rowIndex] = aValue;
    }


    public String getColumnName(int column) {
        if (column == 0)
            return "Index";
        else
            return header.values[column - 1];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

}
