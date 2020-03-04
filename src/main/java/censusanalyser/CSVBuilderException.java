package censusanalyser;

public class CSVBuilderException extends RuntimeException {
    enum ExceptionType {
        CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE;
    }

    CensusAnalyserException.ExceptionType type;
}
