package censusanalyser;

import java.util.Map;

public class CensusAdapterFactory {
    public static Map<String, CensusDTO> getCensusdata(CensusAnalyser.Country country, String[] csvFilePath) {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusAdapter().loadCensusdata(csvFilePath);
        if (country.equals(CensusAnalyser.Country.US))
            return new USCensusAdapter().loadCensusdata(csvFilePath);
            throw new CensusAnalyserException("Incorrect Country", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }
}
