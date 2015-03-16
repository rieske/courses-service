package lt.vv.courses.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import lt.vv.courses.api.model.Course;
import lt.vv.courses.api.model.Participant;
import lt.vv.courses.repository.entities.CourseEntity;
import lt.vv.courses.repository.entities.ParticipantEntity;

import org.junit.Test;

public class EntityToApiModelMapperTest {

	private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");

	EntityToApiModelMapper mapper = new EntityToApiModelMapper();

	@Test
	public void mapsFromCourseEntityToApiModel() throws ParseException {
		CourseEntity entity = mock(CourseEntity.class);
		when(entity.getId()).thenReturn(123L);
		when(entity.getName()).thenReturn("name");
		when(entity.getStartTime()).thenReturn(new Timestamp(dateFormat.parse("2015-01-03T10:15+0000").getTime()));
		when(entity.getEndTime()).thenReturn(new Timestamp(dateFormat.parse("2015-01-03T10:17+0000").getTime()));
		when(entity.getLocation()).thenReturn("location");

		Course course = mapper.fromEntity(entity);

		assertThat(course).isEqualToComparingFieldByField(new Course(
				123,
				"name",
				new Timestamp(dateFormat.parse("2015-01-03T10:15+0000").getTime()),
				new Timestamp(dateFormat.parse("2015-01-03T10:17+0000").getTime()),
				"location"));
	}

	@Test
	public void mapsFromParticipantEntityToApiModel() {
		ParticipantEntity entity = mock(ParticipantEntity.class);
		when(entity.getFirstName()).thenReturn("name");
		when(entity.getLastName()).thenReturn("surname");
		when(entity.getDateOfBirth()).thenReturn(new Date(100));

		Participant participant = mapper.fromEntity(entity);

		assertThat(participant).isEqualToComparingFieldByField(new Participant("name", "surname", 100));
	}
}
