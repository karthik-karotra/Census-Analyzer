package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends CensusAdapter {
    Map<String, CensusDTO> censusCSVMap=  new HashMap<>();

    @Override
    public Map<String, CensusDTO> loadCensusdata(String... csvFilePath) {
        Map<String,CensusDTO> censusCSVMap = super.loadCensusdata(IndiaCensusCSV.class,csvFilePath[0]);
        this.loadIndianStateCode(censusCSVMap,csvFilePath[1]);
        return censusCSVMap;
    }

    public int loadIndianStateCode(Map<String, CensusDTO> censusCSVMap, String indiaStateCsvFilePath)  {
        try (Reader reader = Files.newBufferedReader(Paths.get(indiaStateCsvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndianStateCodeCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader,IndianStateCodeCSV.class);
            Iterable<IndianStateCodeCSV> csvIterable = () -> censusCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(),false)
                    .filter(csvState -> censusCSVMap.get(csvState.state)!=null)
                    .forEach(csvState -> censusCSVMap.get(csvState.state).stateCode=csvState.stateCode);
            return this.censusCSVMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
}
