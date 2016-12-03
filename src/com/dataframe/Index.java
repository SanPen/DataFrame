package com.dataframe;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by santi on 3/12/16.
 */
public class Index {

    Object[] values = null;

    private Map<Object, Integer> value_position = new HashMap<Object, Integer>();


    /**
     *
     * @param data
     */
    public Index(Object[] data){

        values = data;

        // convert and fill the dictionary
        convert();
    }

    /**
     * Try to convert the values to dates
     */
    private void convert(){

        DateUtil date_util = new DateUtil();

        for(int i=0; i<values.length ;i++){
            // try to convert to date object
            values[i] = date_util.stringToDate((String)values[i]);

            // add to the dictionary
            value_position.put(values[i], i);
        }
    }

    /**
     * Return the indices matching an array of strings
     * @param val
     * @return
     */
    public int[] getIndices(Object[] val){
        int[] indices = new int[val.length];
        for(int i=0; i<val.length ;i++)
            indices[i] = value_position.get(val[i]);
        return indices;
    }

}
