package censusanalyser;

public class CensusDTO {
    public double totalArea;
    public double densityPerSqKm;
    public double population;
    public String state;
    public String stateCode;

    public CensusDTO(IndiaCensusCSV indiaCensusCSV) {
        state=indiaCensusCSV.state;
        totalArea =indiaCensusCSV.totalArea;
        densityPerSqKm =indiaCensusCSV.densityPerSqKm;
        population=indiaCensusCSV.population;
    }

    public CensusDTO(USCensusCSV censusCSV) {
        state=censusCSV.state;
        population=censusCSV.population;
        totalArea=censusCSV.totalArea;
        densityPerSqKm =censusCSV.densityPerSqKm;
        stateCode=censusCSV.stateCode;
    }
}
