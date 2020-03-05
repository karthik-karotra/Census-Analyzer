package censusanalyser;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<CensusDTO> censusList=null;
    Map<String, CensusDTO> censusCSVMap= null;

    public CensusAnalyser() {
        this.censusCSVMap =  new HashMap<>();
    }

    public int loadIndiaCensusData(String csvFilePath)  {
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader,IndiaCensusCSV.class);
            while(censusCSVIterator.hasNext()) {
                IndiaCensusCSV indiaCensusCSV = censusCSVIterator.next();
                this.censusCSVMap.put(indiaCensusCSV.state,new CensusDTO(indiaCensusCSV));
            }
            return censusCSVMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    public int loadIndianStateCode(String indiaStateCsvFilePath)  {
        try (Reader reader = Files.newBufferedReader(Paths.get(indiaStateCsvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndianStateCodeCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader,IndianStateCodeCSV.class);
            Iterable<IndianStateCodeCSV> csvIterable = () -> censusCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(),false)
                    .filter(csvState -> censusCSVMap.get(csvState.state)!=null)
                    .forEach(csvState -> censusCSVMap.get(csvState.state).stateCode=csvState.state);
            return censusCSVMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }


    public int loadUSCensusData(String csvFilePath) {
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<USCensusCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader,USCensusCSV.class);
            while(censusCSVIterator.hasNext()) {
                USCensusCSV censusCSV = censusCSVIterator.next();
                this.censusCSVMap.put(censusCSV.state,new CensusDTO(censusCSV));
            }
            return censusCSVMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private<E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable=() -> iterator;
        int numOfRecords=(int) StreamSupport.stream(csvIterable.spliterator(),false).count();
        return numOfRecords;
    }

    public String getStateWiseSortedCensusData() {
        if(censusCSVMap == null || censusCSVMap.size()==0) {
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }

        Comparator<CensusDTO> censusComparator =Comparator.comparing(census -> census.state);
        censusList =censusCSVMap.values().stream().collect(Collectors.toList());
        this.sort(censusComparator);
        String sortedStateCensusJson=new Gson().toJson(censusList);
        return sortedStateCensusJson;

    }

    private void sort(Comparator<CensusDTO> censusComparator) {
        for(int i=0;i<censusList.size();i++) {
            for(int j=0;j<censusList.size()-i-1;j++) {
                CensusDTO census1=censusList.get(j);
                CensusDTO census2=censusList.get(j+1);
                if(censusComparator.compare(census1,census2)>0) {
                    censusList.set(j,census2);
                    censusList.set(j+1,census1);
                }
            }
        }
    }


}
