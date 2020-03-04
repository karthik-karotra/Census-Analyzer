package censusanalyser;

public class CSVBuliderException extends RuntimeException{

    enum ExceptionType {
        CENSUS_FILE_PROBLEM,UNABLE_TO_PASS ;
    }

    ExceptionType type;

    public CSVBuliderException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
