package lt.vv.courses.services;

import lt.vv.courses.api.Course;
import lt.vv.courses.api.Participant;
import lt.vv.courses.repository.entities.CourseEntity;
import lt.vv.courses.repository.entities.ParticipantEntity;

import org.springframework.stereotype.Component;

@Component
public class EntityToApiModelMapper {

	public Course fromEntity(CourseEntity entity) {
		return new Course(
				entity.getId(),
				entity.getName(),
				entity.getStartTime(),
				entity.getEndTime(),
				entity.getLocation());
	}

	public Participant fromEntity(ParticipantEntity entity) {
		return new Participant(
				entity.getFirstName(),
				entity.getLastName(),
				entity.getDateOfBirth().getTime());
	}

}
