# DataFrame
Java libary to handle tables like the Pandas python library DataFrame

An usage example is:
```
import com.dataframe.CSV;
import com.dataframe.DataFrame;


public class Main {

    public static void main(String[] args) {

        ui gui = new ui();

        DataFrame data_frame = new CSV("Data.csv", ";").read();
        gui.setModel(data_frame);
    }
}
```
