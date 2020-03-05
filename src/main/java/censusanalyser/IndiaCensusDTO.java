package censusanalyser;

public class IndiaCensusDTO {

    public int population;
    public int areaInSqKm;
    public int densityPerSqKm;
    public String state;
    public String stateCode;

    public IndiaCensusDTO(IndiaCensusCSV indiaCensus) { 
        state = indiaCensus.state;
        densityPerSqKm = indiaCensus.densityPerSqKm;
        areaInSqKm = indiaCensus.areaInSqKm;
        population = indiaCensus.population;
    }
}
