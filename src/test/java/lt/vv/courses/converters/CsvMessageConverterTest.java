package lt.vv.courses.converters;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import lt.vv.courses.api.model.CsvRecord;

import org.junit.Test;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.mock.http.MockHttpOutputMessage;

import com.google.common.collect.Lists;

public class CsvMessageConverterTest {

	CsvMessageConverter converter = new CsvMessageConverter();

	@Test
	public void supportsCsvRecords() {
		assertThat(converter.supports(CsvRecord.class), is(true));
	}

	@Test
	public void supportsCsvMediaType() {
		assertThat(converter.getSupportedMediaTypes(), contains(CsvMessageConverter.CSV_MEDIA_TYPE));
	}

	@Test
	public void convertsCsvRecordToCsvHttpResponse() throws HttpMessageNotWritableException, IOException {
		CsvRecord csvRecord = new CsvRecord("csvFileName.csv", Lists.newArrayList("record1", "record2", "record3"));
		HttpOutputMessage outputMessage = new MockHttpOutputMessage();

		converter.write(csvRecord, CsvMessageConverter.CSV_MEDIA_TYPE, outputMessage);

		assertThat(outputMessage.getHeaders().getContentType(), equalTo(CsvMessageConverter.CSV_MEDIA_TYPE));
		assertThat(outputMessage.getHeaders().get("Content-Disposition"), hasSize(1));
		assertThat(outputMessage.getHeaders().getFirst("Content-Disposition"), equalTo("attachment; filename=\"csvFileName.csv\""));
		assertThat(outputMessage.getBody().toString(), equalTo("record1\r\nrecord2\r\nrecord3\r\n"));
	}
}
