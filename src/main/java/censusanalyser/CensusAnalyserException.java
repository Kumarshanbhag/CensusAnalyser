package censusanalyser;

public class CensusAnalyserException extends RuntimeException {

    public CensusAnalyserException(String message, String name) {
        super(message);
        ExceptionType.valueOf(name);
    }

    enum ExceptionType {
        CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE, NO_CENSUS_DATA;
    }

    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
