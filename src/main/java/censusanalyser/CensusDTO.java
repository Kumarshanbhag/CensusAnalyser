package censusanalyser;

public class CensusDTO {

    public int population;
    public double totalArea;
    public double populationDensity;
    public String state;
    public String stateCode;

    public CensusDTO(IndiaCensusCSV indiaCensus) {
        state = indiaCensus.state;
        populationDensity =indiaCensus.densityPerSqKm;
        totalArea = indiaCensus.areaInSqKm;
        population = indiaCensus.population;
    }


    public CensusDTO(USCensusCSV usCensus) {
        stateCode = usCensus.stateID;
        state = usCensus.state;
        population = usCensus.population;
        populationDensity = usCensus.populationDensity;
        totalArea = usCensus.totalArea;
    }


}
