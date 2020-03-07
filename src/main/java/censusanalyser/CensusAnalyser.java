package censusanalyser;

import com.google.gson.Gson;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CensusAnalyser {

    public enum Country {
        INDIA, US;
    }

    List<CensusDTO> censusList = null;
    Map<String, CensusDTO> censusCSVMap = new HashMap<>();
    Map<SortField, Comparator<CensusDTO>> sortMap;

    public CensusAnalyser() {
        this.sortMap = new HashMap<>();
        this.sortMap.put(SortField.STATE, Comparator.comparing(census -> census.state));
        this.sortMap.put(SortField.POPULATION, Comparator.comparing(census -> census.population));
        this.sortMap.put(SortField.POPULATIONSDENSITY, Comparator.comparing(census -> census.densityPerSqKm));
        this.sortMap.put(SortField.TOTALAREA, Comparator.comparing(census -> census.totalArea));
        this.sortMap.put(SortField.STATEID, Comparator.comparing(census -> census.stateCode));
    }

    public int loadCensusData(Country country, String... csvFilePath) {
        censusCSVMap = CensusAdapterFactory.getCensusdata(country, csvFilePath);
        return censusCSVMap.size();
    }

    public String getSortedCensusData(SortField sortField) {
        if (censusCSVMap == null || censusCSVMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        censusList = censusCSVMap.values().stream().collect(Collectors.toList());
        this.sort(this.sortMap.get(sortField).reversed());
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    private void sort(Comparator<CensusDTO> censusComparator) {
        for (int i = 0; i < this.censusList.size(); i++) {
            for (int j = 0; j < this.censusList.size() - i - 1; j++) {
                CensusDTO census1 = this.censusList.get(j);
                CensusDTO census2 = this.censusList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    this.censusList.set(j, census2);
                    this.censusList.set(j + 1, census1);
                }
            }
        }
    }
}
