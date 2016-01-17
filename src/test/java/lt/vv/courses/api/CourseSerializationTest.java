package lt.vv.courses.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.time.Instant;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CourseSerializationTest {

	private static final ObjectMapper MAPPER = new ObjectMapper()
			.findAndRegisterModules()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	@Test
	public void serializesCourseToJson() throws JsonProcessingException, ParseException {
		String courseJson = MAPPER.writeValueAsString(new Course(
				1,
				"name",
				Instant.parse("2015-01-03T10:14:00.00Z"),
				Instant.parse("2015-01-03T10:17:00.00Z"),
				"loc"));

		assertThat(courseJson)
				.isEqualTo(
						"{\"id\":1,\"courseName\":\"name\",\"startTime\":\"2015-01-03T10:14:00Z\",\"endTime\":\"2015-01-03T10:17:00Z\",\"location\":\"loc\"}");
	}

}
