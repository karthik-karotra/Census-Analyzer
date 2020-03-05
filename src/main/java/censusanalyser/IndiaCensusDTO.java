package censusanalyser;

public class IndiaCensusDTO {
    public int areaInSqKm;
    public int densityPerSqKm;
    public int population;
    public String state;
    public String stateCode;

    public IndiaCensusDTO(IndiaCensusCSV indiaCensusCSV) {
        state=indiaCensusCSV.state;
        areaInSqKm=indiaCensusCSV.areaInSqKm;
        densityPerSqKm=indiaCensusCSV.densityPerSqKm;
        population=indiaCensusCSV.population;

    }
}
