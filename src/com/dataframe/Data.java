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

        columns = data.length;
        if (columns > 0)
            rows = data[0].length;

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

                if (values[col][row] != null) {
                    try {
                        if (values[col][row] instanceof String) {
                            s = (String) values[col][row];
                            s = s.replace(",", ".");
                            values[col][row] = Double.parseDouble(s);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace(); //prints error
                    }
                }else{
                    values[col][row]=0.0;
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
                        s[col] = (Double) values[col][row];
                    else
                        s[col] += (Double) values[col][row];
                }
        } else {
            s = new Double[rows];
            for (int row = 0; row < rows; row++)
                for (int col = 0; col < columns; col++) {
                    if (s[row] == null)
                        s[row] = (Double) values[col][row];
                    else
                        s[row] += (Double) values[col][row];
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
                s += (Double) values[col][row];
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
