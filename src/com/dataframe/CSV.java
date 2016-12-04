package com.dataframe;

/**
 * Created by santi on 3/12/16.
 */
public class CSV {

    String file_name;

    String delimiter;

    int index_column = -1;

    int header_row = -1;

    boolean parse_dates = false;

    int rows_to_skip = 0;

    public CSV(String fname, String delimiter_, int index_column_, boolean parse_dates_, int rows_to_skip_, int header_row_) {
        file_name = fname;
        delimiter = delimiter_;
        index_column = index_column_;
        parse_dates = parse_dates_;
        rows_to_skip = rows_to_skip_;
        header_row = header_row_;
    }

    public CSV(String fname, String delimiter_) {
        this(fname, delimiter_, 0, true, 0, 0);
    }

    public CSV(String fname) {
        this(fname, ",", 0, true, 0, 0);
    }


    /**
     * @return
     */
    public DataFrame read() {

        // Read the CSV file
        CSVFile file = new CSVFile(file_name, delimiter);

        // Declare the arrays
        Object[][] data ;
        Object[] idx ;
        String[] hdr ;
        int col_offset = 0;
        int row_offset = 0;

        if (header_row == -1) {
            idx = new Object[file.rows];
            if (index_column ==-1) {
                data = new Object[file.columns][file.rows];
                hdr = new String[file.columns];
            }else{
                data = new Object[file.columns-1][file.rows];
                hdr = new String[file.columns-1];
                col_offset = 1;
            }
        }else{
            idx = new Object[file.rows-1];
            row_offset = 1;
            if (index_column ==-1) {
                data = new Object[file.columns][file.rows-1];
                hdr = new String[file.columns];
            }else{
                data = new Object[file.columns-1][file.rows-1];
                hdr = new String[file.columns-1];
                col_offset = 1;
            }
        }

        // Dump the CSV file to the arrays
        for (int row = 0; row < file.rows; row++) {
            if (row + 1 > rows_to_skip) {

                for (int col = 0; col < file.columns; col++) {

                    if (row == header_row) {
                        if (col != index_column) {
                            hdr[col-col_offset] = file.content.get(row)[col];
                        }
                    } else {

                        if (col != index_column) {
                            data[col-col_offset][row-row_offset] = file.content.get(row)[col];
                        }else{
                            idx[row-row_offset] = file.content.get(row)[col];
                        }
                    }
                }
            }
        }


        // Return the DataFrame
        return new DataFrame(data, hdr, idx);
    }


}
