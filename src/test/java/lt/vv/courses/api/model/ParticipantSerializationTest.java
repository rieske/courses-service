package lt.vv.courses.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lt.vv.courses.api.Participant;

public class ParticipantSerializationTest {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Test
	public void serializesParticipantToJson() throws JsonProcessingException {
		String participantJson = MAPPER.writeValueAsString(new Participant("name", "surname", 999));

		assertThat(participantJson).isEqualTo("{\"firstName\":\"name\",\"lastName\":\"surname\",\"dateOfBirth\":999}");
	}

}
