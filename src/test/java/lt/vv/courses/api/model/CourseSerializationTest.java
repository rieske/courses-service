package lt.vv.courses.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CourseSerializationTest {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Test
	public void serializesCourseToJson() throws JsonProcessingException {
		String courseJson = MAPPER.writeValueAsString(new Course(1, "name", 123, 456, "loc"));

		assertThat(courseJson).isEqualTo("{\"id\":1,\"courseName\":\"name\",\"startTime\":123,\"endTime\":456,\"location\":\"loc\"}");
	}

}
