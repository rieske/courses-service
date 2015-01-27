package lt.vv.courses.converters;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;

import lt.vv.courses.api.model.CsvRecord;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

@Component
public class CsvMessageConverter extends AbstractHttpMessageConverter<CsvRecord> {

	public static final MediaType CSV_MEDIA_TYPE = new MediaType("text", "csv", Charset.forName("utf-8"));

	public CsvMessageConverter() {
		super(CSV_MEDIA_TYPE);
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return CsvRecord.class.equals(clazz);
	}

	@Override
	protected CsvRecord readInternal(Class<? extends CsvRecord> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	protected void writeInternal(CsvRecord csvRecord, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException
	{
		outputMessage.getHeaders().setContentType(CSV_MEDIA_TYPE);
		outputMessage.getHeaders().set("Content-Disposition", "attachment; filename=\"" + csvRecord.getFileName() + "\"");

		try (CSVPrinter printer = CSVFormat.EXCEL.print(new PrintStream(outputMessage.getBody(), true, "UTF-8"))) {
			printer.printRecords(csvRecord.getSerializedRecords());
		}
	}

}
