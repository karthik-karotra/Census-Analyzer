package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusLoader {
    Map<String, CensusDTO> censusCSVMap=  new HashMap<>();
    public <E> Map<String,CensusDTO>loadCensusdata(Class<E> censusCSVClass, String... csvFilePath) {

        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> censusCSVIterator = csvBuilder.getCSVFileIterator(reader,censusCSVClass);
            Iterable<E> csvIterable = () -> censusCSVIterator;
            if(censusCSVClass.getName().equals("censusanalyser.IndiaCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new CensusDTO(censusCSV)));
            }
            else if(censusCSVClass.getName().equals("censusanalyser.USCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new CensusDTO(censusCSV)));

            }

            if(csvFilePath.length == 1)  return censusCSVMap;
            this.loadIndianStateCode(censusCSVMap,csvFilePath[1]);
            return censusCSVMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private int loadIndianStateCode(Map<String, CensusDTO> censusCSVMap, String indiaStateCsvFilePath)  {
        try (Reader reader = Files.newBufferedReader(Paths.get(indiaStateCsvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndianStateCodeCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader,IndianStateCodeCSV.class);
            Iterable<IndianStateCodeCSV> csvIterable = () -> censusCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(),false)
                    .filter(csvState -> this.censusCSVMap.get(csvState.state)!=null)
                    .forEach(csvState -> this.censusCSVMap.get(csvState.state).stateCode=csvState.state);
            return this.censusCSVMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
}
