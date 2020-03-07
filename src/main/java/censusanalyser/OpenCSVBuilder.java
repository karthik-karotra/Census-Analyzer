package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCSVBuilder implements ICSVBuilder{

    @Override
    public<E> Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvClass) {
        return this.getCSVBean(reader,csvClass).iterator();

    }

    @Override
    public <E> List<E> getCSVFileList(Reader reader, Class<E> csvClass) {
        return this.getCSVBean(reader,csvClass).parse();

    }

    private <E> CsvToBean getCSVBean(Reader reader, Class<E> csvClass) {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
           /* Iterator<E> censusCSVIterator = csvToBean.iterator();*/
            return csvToBean;
        }
        catch (IllegalStateException e)
        {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.UNABLE_TO_PASS);

        }
    }
}
