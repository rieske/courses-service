package lt.vv.courses.services;

import lt.vv.courses.api.Course;
import lt.vv.courses.api.Participant;
import lt.vv.courses.repository.entities.CourseEntity;
import lt.vv.courses.repository.entities.ParticipantEntity;
import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EntityToApiMapperTest {

	private EntityToApiMapper mapper = new EntityToApiMapper();

	@Test
	public void mapsFromCourseEntityToApiModel() {
		CourseEntity entity = mock(CourseEntity.class);
		when(entity.getId()).thenReturn(123L);
		when(entity.getName()).thenReturn("name");
		when(entity.getStartTime()).thenReturn(new Timestamp(Instant.parse("2015-01-03T10:15:00.00Z").toEpochMilli()));
		when(entity.getEndTime()).thenReturn(new Timestamp(Instant.parse("2015-01-03T10:17:00.00Z").toEpochMilli()));
		when(entity.getLocation()).thenReturn("location");

		Course course = mapper.fromEntity(entity);

		assertThat(course).isEqualToComparingFieldByField(new Course(
				123,
				"name",
				Instant.parse("2015-01-03T10:15:00.00Z"),
				Instant.parse("2015-01-03T10:17:00.00Z"),
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
