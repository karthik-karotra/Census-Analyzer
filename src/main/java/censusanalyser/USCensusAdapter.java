package censusanalyser;
import java.util.Map;

public  class USCensusAdapter extends CensusAdapter{

    @Override
    public Map<String, CensusDTO> loadCensusdata(String... csvFilePath) {
        Map<String,CensusDTO> censusCSVMap = super.loadCensusdata(USCensusCSV.class,csvFilePath[0]);
        return censusCSVMap;

    }
}
