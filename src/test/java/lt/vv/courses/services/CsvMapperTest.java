package lt.vv.courses.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import lt.vv.courses.api.model.Participant;

import org.junit.Test;

import com.google.common.collect.Lists;

public class CsvMapperTest {

	CsvMapper csvMapper = new CsvMapper();

	@Test
	public void mapsParticipantListToCsvStringList() {
		List<String> participantCsvs = csvMapper.toCsv(Lists.newArrayList(
				new Participant("name1", "surname1", 111),
				new Participant("name2", "surname2", 222)));

		assertThat(participantCsvs).containsExactly("name1,surname1,111", "name2,surname2,222");
	}

	@Test
	public void mapsEmptyParticipantListToEmptyCsvStringList() {
		List<String> participantCsvs = csvMapper.toCsv(Lists.<Participant> newArrayList());

		assertThat(participantCsvs).isEmpty();
	}

}
