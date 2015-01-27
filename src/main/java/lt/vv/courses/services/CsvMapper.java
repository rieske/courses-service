package lt.vv.courses.services;

import java.util.List;

import lt.vv.courses.api.model.Participant;

import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

@Component
public class CsvMapper {

	private static final String SEPARATOR = ",";
	private static final Joiner JOINER = Joiner.on(SEPARATOR);

	public List<String> toCsv(List<Participant> participants) {
		List<String> participantCsvs = Lists.newArrayList();
		for (Participant participant : participants) {
			participantCsvs.add(JOINER.join(participant.getFirstName(), participant.getLastName(), participant.getDateOfBirth()));
		}
		return participantCsvs;
	}
}
