package censusanalyser;

public class CSVBuilderException extends RuntimeException{

    enum ExceptionType {
        CENSUS_FILE_PROBLEM,UNABLE_TO_PASS ;
    }

    ExceptionType type;

    public CSVBuilderException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
