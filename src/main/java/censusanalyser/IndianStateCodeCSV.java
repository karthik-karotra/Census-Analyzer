package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndianStateCodeCSV {

    @CsvBindByName(column = "State Name", required = true)
    public String state;

    @CsvBindByName(column = "StateCode", required = true)
    public String code;

    @Override
    public String toString() {
        return "IndiaStateCodeCSV{" +
                "state='" + state + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
