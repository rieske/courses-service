package lt.vv.courses.services;

import lt.vv.courses.api.Participant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CsvMapper {

    private static final String SEPARATOR = ",";

    public List<String> toCsv(List<Participant> participants) {
        return participants.stream()
                .map(participant -> String.join(SEPARATOR, participant.firstName, participant.lastName, String.valueOf(participant.dateOfBirth)))
                .collect(Collectors.toList());
    }
}
