package lt.vv.courses.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lt.vv.courses.api.Course;

public class CourseSerializationTest {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");

	@Test
	public void serializesCourseToJson() throws JsonProcessingException, ParseException {
		String courseJson = MAPPER.writeValueAsString(new Course(
				1,
				"name",
				dateFormat.parse("2015-01-03T10:14+0000"),
				dateFormat.parse("2015-01-03T10:17+0000"),
				"loc"));

		assertThat(courseJson)
				.isEqualTo(
						"{\"id\":1,\"courseName\":\"name\",\"startTime\":\"2015-01-03T10:14+0000\",\"endTime\":\"2015-01-03T10:17+0000\",\"location\":\"loc\"}");
	}

}
