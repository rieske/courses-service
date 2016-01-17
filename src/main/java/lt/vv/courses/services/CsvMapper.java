package lt.vv.courses.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;

import lt.vv.courses.api.Participant;

@Component
public class CsvMapper {

	private static final String SEPARATOR = ",";
	private static final Joiner JOINER = Joiner.on(SEPARATOR);

	public List<String> toCsv(List<Participant> participants) {
		return participants.stream()
				.map(participant -> JOINER.join(participant.firstName, participant.lastName, participant.dateOfBirth))
				.collect(Collectors.toList());
	}
}
