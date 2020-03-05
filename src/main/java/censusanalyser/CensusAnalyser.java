package censusanalyser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<CensusDTO> censusList = null ;
    Map<String, CensusDTO> censusMap = null;

    public int loadIndiaCensusData(String csvFilePath) {

        censusMap = new CensusLoader().loadCensusData(csvFilePath, IndiaCensusCSV.class);
        censusList = censusMap.values().stream().collect(Collectors.toList());
        return censusMap.size();
    }

    public int loadUSCensusData(String csvFilePath) {

        censusMap = new CensusLoader().loadCensusData(csvFilePath, USCensusCSV.class);
        return censusMap.size();
    }

    public int loadIndiaStateCode(String csvFilePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCSVFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> stateCSVFileIterator;
            StreamSupport.stream(csvIterable.spliterator(),false)
                    .filter(csvState -> censusMap.get(csvState.stateCode) != null)
                    .forEach(csvState -> censusMap.get(csvState.stateCode).stateCode = csvState.stateCode);
            return censusMap.size();

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }  catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
        return numOfEntries;
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException{
        if(censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator <CensusDTO> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(censusComparator);
        String sortedStateCensus = new Gson().toJson(censusList);
        return sortedStateCensus;
    }

    private void sort(Comparator<CensusDTO> censusComparator) {
        for(int i=0; i<censusList.size() - 1; i++) {
            for (int j=0; j<censusList.size() - i -1; j++ ) {
                CensusDTO census1 = censusList.get(j);
                CensusDTO census2 = censusList.get(j+1);
                if(censusComparator.compare(census1, census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j+1, census1);
                }
            }
        }

    }


}
