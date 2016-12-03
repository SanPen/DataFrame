package com.dataframe;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by santi on 3/12/16.
 */
public class Header {

    String[] values = null;

    /**
     * Dictionary to relate column names with their indices
     */
    private Map<String, Integer> value_position = new HashMap<String, Integer>();

    /**
     *
     * @param data
     */
    public Header(String[] data){

        values = data;

        // fil the dictionary
        for(int i=0; i<data.length ;i++)
            value_position.put(data[i], i);

    }


    /**
     * Return the indices matching an array of strings
     * @param val
     * @return
     */
    public int[] getIndices(String[] val){
        int[] indices = new int[val.length];
        for(int i=0; i<val.length ;i++)
            indices[i] = value_position.get(val[i]);
        return indices;
    }
}
