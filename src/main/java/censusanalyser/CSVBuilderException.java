package censusanalyser;

public class CSVBuilderException extends RuntimeException{

    enum ExceptionType {
        UNABLE_TO_PASS ;
    }

    ExceptionType type;

    public CSVBuilderException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
