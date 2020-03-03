package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {

            Iterator<IndiaCensusCSV> censusCSVIterator = getCSVFileIterator(reader,IndiaCensusCSV.class);

            Iterable<IndiaCensusCSV> csvIterable=() -> censusCSVIterator;
            int numOfRecords=(int) StreamSupport.stream(csvIterable.spliterator(),false).count();
            return numOfRecords;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }



    public int loadIndianStateCode(String indiaStateCsvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(indiaStateCsvFilePath))) {
            Iterator<IndianStateCodeCSV> censusCSVIterator = getCSVFileIterator(reader,IndianStateCodeCSV.class);
            Iterable<IndianStateCodeCSV> csvIterable = () -> censusCSVIterator;
            int stateCodeCount = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
            return stateCodeCount;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PASS);
        }
    }


    private<E> Iterator<E> getCSVFileIterator(Reader reader,Class csvClass) {
        CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(csvClass);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        CsvToBean<E> csvToBean = csvToBeanBuilder.build();
        Iterator<E> censusCSVIterator = csvToBean.iterator();
        return censusCSVIterator;
    }

  /*  private Iterator<IndianStateCodeCSV> getStateCSVFileIterator(Reader reader) {
        CsvToBeanBuilder<IndianStateCodeCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(IndianStateCodeCSV.class);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        CsvToBean<IndianStateCodeCSV> csvToBean = csvToBeanBuilder.build();
        return csvToBean.iterator();
    }*/
}
