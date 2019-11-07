package lt.vv.courses.services;

import lt.vv.courses.api.Participant;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvMapperTest {

    private CsvMapper csvMapper = new CsvMapper();

    @Test
    public void mapsParticipantListToCsvStringList() {
        List<String> participantCsvs = csvMapper.toCsv(Arrays.asList(
                new Participant("name1", "surname1", 111),
                new Participant("name2", "surname2", 222)));

        assertThat(participantCsvs).containsExactly("name1,surname1,111", "name2,surname2,222");
    }

    @Test
    public void mapsEmptyParticipantListToEmptyCsvStringList() {
        List<String> participantCsvs = csvMapper.toCsv(Collections.emptyList());

        assertThat(participantCsvs).isEmpty();
    }

}
