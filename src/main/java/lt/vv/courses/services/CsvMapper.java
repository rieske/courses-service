package lt.vv.courses.services;

import java.util.List;
import java.util.stream.Collectors;

import lt.vv.courses.api.model.Participant;

import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;

@Component
public class CsvMapper {

	private static final String SEPARATOR = ",";
	private static final Joiner JOINER = Joiner.on(SEPARATOR);

	public List<String> toCsv(List<Participant> participants) {
		return participants.stream()
				.map(participant -> JOINER.join(participant.getFirstName(), participant.getLastName(), participant.getDateOfBirth()))
				.collect(Collectors.toList());
	}
}
