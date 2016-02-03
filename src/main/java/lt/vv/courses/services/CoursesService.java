package lt.vv.courses.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import lt.vv.courses.api.Course;
import lt.vv.courses.api.CourseNotFound;
import lt.vv.courses.api.Participant;
import lt.vv.courses.repository.CourseRepository;
import lt.vv.courses.repository.ParticipantRepository;

@Service
public class CoursesService {

	@Autowired
	private EntityToApiMapper mapper;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private ParticipantRepository participantRepository;

	public List<Course> findCourses(Optional<LocalDateTime> startTime, Optional<LocalDateTime> endTime) {
		return startTime
				.map(s -> endTime.map(e -> courseRepository.findByStartTimeAfterAndEndTimeBefore(Timestamp.valueOf(s), Timestamp.valueOf(e)))
						.orElse(courseRepository.findByStartTimeAfterOrderByStartTimeAsc(Timestamp.valueOf(s))))
				.orElseGet(() -> endTime.map(e -> courseRepository.findByEndTimeBeforeOrderByEndTimeDesc(Timestamp.valueOf(e)))
						.orElse(courseRepository.findAll(new Sort(Direction.ASC, "name"))))
				.stream()
				.map(mapper::fromEntity)
				.collect(Collectors.toList());
	}

	public List<Participant> findCourseParticipants(long courseId) throws CourseNotFound {
		courseRepository.findById(courseId).orElseThrow(CourseNotFound::new);
		return participantRepository.findByCourseId(courseId).stream()
				.map(mapper::fromEntity)
				.collect(Collectors.toList());
	}
}
