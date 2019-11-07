package lt.vv.courses.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParticipantSerializationTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void serializesParticipantToJson() throws JsonProcessingException {
        String participantJson = MAPPER.writeValueAsString(new Participant("name", "surname", 999));

        assertThat(participantJson).isEqualTo("{\"firstName\":\"name\",\"lastName\":\"surname\",\"dateOfBirth\":999}");
    }

}
