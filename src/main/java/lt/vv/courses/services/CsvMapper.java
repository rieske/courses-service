package lt.vv.courses.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;


import lt.vv.courses.api.Participant;

@Component
public class CsvMapper {

	private static final String SEPARATOR = ",";

	public List<String> toCsv(List<Participant> participants) {
		return participants.stream()
				.map(participant -> String.join(SEPARATOR, participant.firstName, participant.lastName, String.valueOf(participant.dateOfBirth)))
				.collect(Collectors.toList());
	}
}
