package lt.vv.courses.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class CourseSerializationTest {

	private static final ObjectMapper MAPPER = new ObjectMapper()
			.findAndRegisterModules()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	@Test
	public void serializesCourseToJson() throws JsonProcessingException {
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
