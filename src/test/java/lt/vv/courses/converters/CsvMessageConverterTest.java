package lt.vv.courses.converters;

import lt.vv.courses.api.CsvRecord;
import org.junit.Test;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.mock.http.MockHttpOutputMessage;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvMessageConverterTest {

	private CsvMessageConverter converter = new CsvMessageConverter();

	@Test
	public void supportsCsvRecords() {
		assertThat(converter.supports(CsvRecord.class)).isTrue();
	}

	@Test
	public void supportsCsvMediaType() {
		assertThat(converter.getSupportedMediaTypes()).containsExactly(CsvMessageConverter.CSV_MEDIA_TYPE);
	}

	@Test
	public void convertsCsvRecordToCsvHttpResponse() throws HttpMessageNotWritableException, IOException {
		CsvRecord csvRecord = new CsvRecord("csvFileName.csv", Arrays.asList("record1", "record2", "record3"));
		HttpOutputMessage outputMessage = new MockHttpOutputMessage();

		converter.write(csvRecord, CsvMessageConverter.CSV_MEDIA_TYPE, outputMessage);

		assertThat(outputMessage.getHeaders().getContentType()).isEqualTo(CsvMessageConverter.CSV_MEDIA_TYPE);
		assertThat(outputMessage.getHeaders().get("Content-Disposition")).hasSize(1);
		assertThat(outputMessage.getHeaders().getFirst("Content-Disposition")).isEqualTo("attachment; filename=\"csvFileName.csv\"");
		assertThat(outputMessage.getBody().toString()).isEqualTo("record1\r\nrecord2\r\nrecord3\r\n");
	}
}
