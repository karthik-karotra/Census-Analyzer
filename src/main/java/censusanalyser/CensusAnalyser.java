package censusanalyser;
import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {




    public enum Country {INDIA,US;
    }
    List<CensusDTO> censusList=null;
    Map<String, CensusDTO> censusCSVMap= null;

    public CensusAnalyser() {
        this.censusCSVMap =  new HashMap<>();
    }

    public int loadCensusData(Country country,String... csvFilePath)  {
        censusCSVMap=CensusAdapterFactory.getCensusdata(country,csvFilePath);
         return censusCSVMap.size();

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
    public String getPopulationWiseSortedCensusData() {
        if(censusCSVMap == null || censusCSVMap.size()==0) {
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDTO> censusComparator =Comparator.comparing(census -> census.population);
        censusList =censusCSVMap.values().stream().collect(Collectors.toList());
        this.sort(censusComparator);
        String sortedStateCensusJson=new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    public String getPopulationDensityWiseSortedCensusData() {
        if(censusCSVMap == null || censusCSVMap.size()==0) {
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDTO> censusComparator =Comparator.comparing(census -> census.populationDensity);
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
