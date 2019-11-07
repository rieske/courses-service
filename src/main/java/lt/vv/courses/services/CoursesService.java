package lt.vv.courses.services;

import lt.vv.courses.api.Course;
import lt.vv.courses.api.CourseNotFound;
import lt.vv.courses.api.Participant;
import lt.vv.courses.repository.CourseRepository;
import lt.vv.courses.repository.ParticipantRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoursesService {

    private final EntityToApiMapper mapper;
    private final CourseRepository courseRepository;
    private final ParticipantRepository participantRepository;

    public CoursesService(EntityToApiMapper mapper, CourseRepository courseRepository, ParticipantRepository participantRepository) {
        this.mapper = mapper;
        this.courseRepository = courseRepository;
        this.participantRepository = participantRepository;
    }

    public List<Course> findCourses(Optional<LocalDateTime> startTime, Optional<LocalDateTime> endTime) {
        // I'd argue that a series of if statements would read better in this
        // case. Did this functional voodoo for fun as an exercise though
        return startTime
                .map(Timestamp::valueOf)
                .map(s -> endTime
                        .map(Timestamp::valueOf)
                        .map(e -> courseRepository.findByStartTimeAfterAndEndTimeBefore(s, e))
                        .orElseGet(() -> courseRepository.findByStartTimeAfterOrderByStartTimeAsc(s)))
                .orElseGet(() -> endTime
                        .map(Timestamp::valueOf)
                        .map(courseRepository::findByEndTimeBeforeOrderByEndTimeDesc)
                        .orElseGet(() -> courseRepository.findAll(Sort.by(Direction.ASC, "name"))))
                .stream()
                .map(mapper::fromEntity)
                .collect(Collectors.toList());
    }

    public List<Participant> findCourseParticipants(long courseId) throws CourseNotFound {
        return courseRepository.findById(courseId)
                .map(course -> participantRepository.findByCourseId(courseId).stream()
                        .map(mapper::fromEntity)
                        .collect(Collectors.toList()))
                .orElseThrow(CourseNotFound::new);
    }
}
