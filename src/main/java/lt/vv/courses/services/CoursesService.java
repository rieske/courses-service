package lt.vv.courses.services;

import java.util.List;
import java.util.Optional;

import lt.vv.courses.api.model.Course;
import lt.vv.courses.api.model.CourseNotFound;
import lt.vv.courses.api.model.Participant;
import lt.vv.courses.repository.CourseRepository;
import lt.vv.courses.repository.ParticipantRepository;
import lt.vv.courses.repository.entities.CourseEntity;
import lt.vv.courses.repository.entities.ParticipantEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class CoursesService {

	@Autowired
	private EntityToApiModelMapper mapper;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private ParticipantRepository participantRepository;

	public List<Course> findCourses(Optional<Long> startTime, Optional<Long> endTime) {
		List<CourseEntity> courseEntitiesMatchingCriteria = Lists.newArrayList();
		if (startTime.isPresent() && endTime.isPresent()) {
			courseEntitiesMatchingCriteria = courseRepository.findByStartTimeAfterAndEndTimeBefore(startTime.get(), endTime.get());
		} else if (startTime.isPresent()) {
			courseEntitiesMatchingCriteria = courseRepository.findByStartTimeAfterOrderByStartTimeAsc(startTime.get());
		} else if (endTime.isPresent()) {
			courseEntitiesMatchingCriteria = courseRepository.findByEndTimeBeforeOrderByEndTimeDesc(endTime.get());
		} else {
			courseEntitiesMatchingCriteria = courseRepository.findAll(new Sort(Direction.ASC, "name"));
		}

		List<Course> courses = Lists.newArrayList();
		for (CourseEntity entity : courseEntitiesMatchingCriteria) {
			courses.add(mapper.fromEntity(entity));
		}
		return courses;
	}

	public List<Participant> findCourseParticipants(long courseId) throws CourseNotFound {
		Optional<CourseEntity> course = courseRepository.findById(courseId);
		if (!course.isPresent()) {
			throw new CourseNotFound();
		}
		List<Participant> courseParticipants = Lists.newArrayList();
		for (ParticipantEntity entity : participantRepository.findByCourseId(courseId)) {
			courseParticipants.add(mapper.fromEntity(entity));
		}
		return courseParticipants;
	}
}
