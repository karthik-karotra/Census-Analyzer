package censusanalyser;

public class CensusDTO {
    public double totalArea;
    public double populationDensity;
    public int population;
    public String state;
    public String stateCode;

    public CensusDTO(IndiaCensusCSV indiaCensusCSV) {
        state=indiaCensusCSV.state;
        totalArea =indiaCensusCSV.areaInSqKm;
        populationDensity =indiaCensusCSV.densityPerSqKm;
        population=indiaCensusCSV.population;

    }

    public CensusDTO(USCensusCSV censusCSV) {
        state=censusCSV.state;
        population=censusCSV.population;
        totalArea=censusCSV.totalArea;
        populationDensity=censusCSV.populationDensity;
        stateCode=censusCSV.stateId;


    }
}
