package lt.vv.courses.api.model;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ParticipantSerializationTest {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Test
	public void serializesParticipantToJson() throws JsonProcessingException {
		String participantJson = MAPPER.writeValueAsString(new Participant("name", "surname", 999));

		assertThat(participantJson, equalTo("{\"firstName\":\"name\",\"lastName\":\"surname\",\"dateOfBirth\":999}"));
	}

}
