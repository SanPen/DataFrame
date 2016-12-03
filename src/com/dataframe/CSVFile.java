package com.dataframe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class to contain the result of a simple CSV file read
 */
public class CSVFile {

    ArrayList<String[]> content = new ArrayList<>();
    int columns = 0;
    int rows = 0;

    public CSVFile(String fname, String delimiter){

            BufferedReader buffer = null;

            try {
                String line;
                buffer = new BufferedReader(new FileReader(fname));

                // How to read file in java line by line?
                while ((line = buffer.readLine()) != null) {
                    String[] splitData = line.split(delimiter);

                    if (splitData.length > columns)
                        columns=splitData.length;

                    content.add(splitData);
                    rows ++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (buffer != null) buffer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

    }

}
