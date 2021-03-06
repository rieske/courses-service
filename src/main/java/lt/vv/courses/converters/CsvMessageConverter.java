package lt.vv.courses.converters;

import lt.vv.courses.api.CsvRecord;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

@Component
public class CsvMessageConverter extends AbstractHttpMessageConverter<CsvRecord> {

    public static final MediaType CSV_MEDIA_TYPE = new MediaType("text", "csv", StandardCharsets.UTF_8);

    public CsvMessageConverter() {
        super(CSV_MEDIA_TYPE);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return CsvRecord.class.equals(clazz);
    }

    @Override
    protected CsvRecord readInternal(Class<? extends CsvRecord> clazz, HttpInputMessage inputMessage)
            throws HttpMessageNotReadableException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void writeInternal(CsvRecord csvRecord, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        outputMessage.getHeaders().setContentType(CSV_MEDIA_TYPE);
        outputMessage.getHeaders().set("Content-Disposition", "attachment; filename=\"" + csvRecord.fileName + "\"");

        try (CSVPrinter printer = CSVFormat.EXCEL.print(new PrintStream(outputMessage.getBody(), true, "UTF-8"))) {
            printer.printRecords(csvRecord.serializedRecords);
        }
    }

}
