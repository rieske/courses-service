package lt.vv.courses.services;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;

import lt.vv.courses.api.model.Course;
import lt.vv.courses.api.model.Participant;
import lt.vv.courses.repository.entities.CourseEntity;
import lt.vv.courses.repository.entities.ParticipantEntity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;

public class EntityToApiModelMapperTest {

	EntityToApiModelMapper mapper = new EntityToApiModelMapper();

	@Test
	public void mapsFromCourseEntityToApiModel() {
		CourseEntity entity = mock(CourseEntity.class);
		when(entity.getId()).thenReturn(123L);
		when(entity.getName()).thenReturn("name");
		when(entity.getStartTime()).thenReturn(111L);
		when(entity.getEndTime()).thenReturn(222L);
		when(entity.getLocation()).thenReturn("location");

		Course course = mapper.fromEntity(entity);

		assertThat(EqualsBuilder.reflectionEquals(course, new Course(123, "name", 111, 222, "location")), is(true));
	}

	@Test
	public void mapsFromParticipantEntityToApiModel() {
		ParticipantEntity entity = mock(ParticipantEntity.class);
		when(entity.getFirstName()).thenReturn("name");
		when(entity.getLastName()).thenReturn("surname");
		when(entity.getDateOfBirth()).thenReturn(new Date(100));

		Participant participant = mapper.fromEntity(entity);

		assertThat(EqualsBuilder.reflectionEquals(participant, new Participant("name", "surname", 100)), is(true));
	}
}
