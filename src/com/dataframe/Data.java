package com.dataframe;


public class Data {

    Object[][] values = null;

    int rows = 0;
    int columns = 0;

    /**
     * Class constructor
     *
     * @param data: Matrix of values
     */
    public Data(Object[][] data) {

        this.values = data;

        rows = data.length;
        if (rows > 0)
            columns = data[0].length;

        convert_data_types();
    }

    /**
     * Identifies the types contained within the objects
     */
    private void convert_data_types(){

        String s;

        for (int col = 0; col < columns; col++){
            for (int row = 0; row < rows; row++) {
//                convert(values[row][col]);

                if (values[row][col] != null) {
                    try {
                        s = (String) values[row][col];
                        s = s.replace(",", ".");
                        values[row][col] = Double.parseDouble(s);
                    } catch (NumberFormatException e) {
                        e.printStackTrace(); //prints error
                    }
                }else{
                    values[row][col]=0.0;
                }
            }
        }
        System.out.println("Conv");
    }


    private void convert(Object val){

        if (val != null) {
            try {
                val = Double.parseDouble((String) val);
            } catch (NumberFormatException e) {
                e.printStackTrace(); //prints error
            }
        }else{
            val=0.0;
        }
    }

    /**
     * Perform the summation of the values along the specified axis
     * @param axis axis=0 for row summation, 1 for column summation
     * @return Array of sum values
     */
    public Double[] sum(int axis) {
        Double[] s;
        if (axis == 0) {
            s = new Double[columns];
            for (int col = 0; col < columns; col++)
                for (int row = 0; row < rows; row++) {
                    if (s[col] == null)
                        s[col] = (Double) values[row][col];
                    else
                        s[col] += (Double) values[row][col];
                }
        } else {
            s = new Double[rows];
            for (int row = 0; row < rows; row++)
                for (int col = 0; col < columns; col++) {
                    if (s[row] == null)
                        s[row] = (Double) values[row][col];
                    else
                        s[row] += (Double) values[row][col];
                }
        }
        return s;
    }

    /**
     * Perform the summation of all the values
     * @return
     */
    public double sum() {
        double s = 0;
        for (int col = 0; col < columns; col++)
            for (int row = 0; row < rows; row++)
                s += (Double) values[row][col];
        return s;
    }

    /**
     * Return the mean over the specified axis
     * @param axis
     * @return
     */
    public Double[] mean(int axis){

        Double[] summation = sum(axis);

        if (axis == 0){
            for (int i=0; i<summation.length; i++)
                summation[i] /= rows;
        }else{
            for (int i=0; i<summation.length; i++)
                summation[i] /= columns;
        }

        return summation;
    }
}
