package lt.vv.courses.services;

import lt.vv.courses.api.Course;
import lt.vv.courses.api.CourseNotFound;
import lt.vv.courses.api.Participant;
import lt.vv.courses.repository.CourseRepository;
import lt.vv.courses.repository.ParticipantRepository;
import lt.vv.courses.repository.entities.CourseEntity;
import lt.vv.courses.repository.entities.ParticipantEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CoursesServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EntityToApiMapper mapper;

    @Mock
    private ParticipantRepository participantRepository;

    @InjectMocks
    private CoursesService courseService;

    @Test
    public void findsAllCourses() {
        CourseEntity firstCourseEntity = mock(CourseEntity.class);
        CourseEntity secondCourseEntity = mock(CourseEntity.class);
        when(courseRepository.findAll(Sort.by(Direction.ASC, "name"))).thenReturn(
                Arrays.asList(firstCourseEntity, secondCourseEntity));
        Course firstExpectedCourse = mock(Course.class);
        when(mapper.fromEntity(firstCourseEntity)).thenReturn(firstExpectedCourse);
        Course secondExpectedCourse = mock(Course.class);
        when(mapper.fromEntity(secondCourseEntity)).thenReturn(secondExpectedCourse);

        List<Course> courses = courseService.findCourses(Optional.empty(), Optional.empty());

        assertThat(courses).hasSize(2);
        assertThat(courses.get(0)).isSameAs(firstExpectedCourse);
        assertThat(courses.get(1)).isSameAs(secondExpectedCourse);
    }

    @Test
    public void findsCoursesAfterGivenStartTime() {
        CourseEntity firstCourseEntity = mock(CourseEntity.class);
        CourseEntity secondCourseEntity = mock(CourseEntity.class);
        when(courseRepository.findByStartTimeAfterOrderByStartTimeAsc(Timestamp.valueOf(LocalDateTime.parse("2015-01-01T00:00:00")))).thenReturn(
                Arrays.asList(firstCourseEntity, secondCourseEntity));
        Course firstExpectedCourse = mock(Course.class);
        when(mapper.fromEntity(firstCourseEntity)).thenReturn(firstExpectedCourse);
        Course secondExpectedCourse = mock(Course.class);
        when(mapper.fromEntity(secondCourseEntity)).thenReturn(secondExpectedCourse);

        List<Course> courses = courseService.findCourses(Optional.of(LocalDateTime.parse("2015-01-01T00:00:00")), Optional.empty());

        assertThat(courses).hasSize(2);
        assertThat(courses.get(0)).isSameAs(firstExpectedCourse);
        assertThat(courses.get(1)).isSameAs(secondExpectedCourse);
    }

    @Test
    public void findsCoursesBeforeGivenEndTime() {
        CourseEntity firstCourseEntity = mock(CourseEntity.class);
        CourseEntity secondCourseEntity = mock(CourseEntity.class);
        when(courseRepository.findByEndTimeBeforeOrderByEndTimeDesc(Timestamp.valueOf(LocalDateTime.parse("2015-01-01T00:00:00")))).thenReturn(
                Arrays.asList(firstCourseEntity, secondCourseEntity));
        Course firstExpectedCourse = mock(Course.class);
        when(mapper.fromEntity(firstCourseEntity)).thenReturn(firstExpectedCourse);
        Course secondExpectedCourse = mock(Course.class);
        when(mapper.fromEntity(secondCourseEntity)).thenReturn(secondExpectedCourse);

        List<Course> courses = courseService.findCourses(Optional.empty(), Optional.of(LocalDateTime.parse("2015-01-01T00:00:00")));

        assertThat(courses).hasSize(2);
        assertThat(courses.get(0)).isSameAs(firstExpectedCourse);
        assertThat(courses.get(1)).isSameAs(secondExpectedCourse);
    }

    @Test
    public void findsCoursesWithinGivenTimeframe() {
        CourseEntity firstCourseEntity = mock(CourseEntity.class);
        CourseEntity secondCourseEntity = mock(CourseEntity.class);
        when(
                courseRepository.findByStartTimeAfterAndEndTimeBefore(
                        Timestamp.valueOf(LocalDateTime.parse("2015-01-01T00:00:00")),
                        Timestamp.valueOf(LocalDateTime.parse("2015-01-05T00:00:00"))))
                .thenReturn(
                        Arrays.asList(firstCourseEntity, secondCourseEntity));
        Course firstExpectedCourse = mock(Course.class);
        when(mapper.fromEntity(firstCourseEntity)).thenReturn(firstExpectedCourse);
        Course secondExpectedCourse = mock(Course.class);
        when(mapper.fromEntity(secondCourseEntity)).thenReturn(secondExpectedCourse);

        List<Course> courses = courseService.findCourses(Optional.of(LocalDateTime.parse("2015-01-01T00:00:00")),
                Optional.of(LocalDateTime.parse("2015-01-05T00:00:00")));

        assertThat(courses).hasSize(2);
        assertThat(courses.get(0)).isSameAs(firstExpectedCourse);
        assertThat(courses.get(1)).isSameAs(secondExpectedCourse);
    }

    @Test(expected = CourseNotFound.class)
    public void throwsCourseNotFoundForNonexistentCourseParticipantSearch() throws CourseNotFound {
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        courseService.findCourseParticipants(999L);
    }

    @Test
    public void findsCourseParticipants() throws CourseNotFound {
        when(courseRepository.findById(123L)).thenReturn(Optional.of(mock(CourseEntity.class)));
        ParticipantEntity firstParticipantEntity = mock(ParticipantEntity.class);
        ParticipantEntity secondParticipantEntity = mock(ParticipantEntity.class);
        when(participantRepository.findByCourseId(123L)).thenReturn(
                Arrays.asList(firstParticipantEntity, secondParticipantEntity));
        Participant firstExpectedParticipant = mock(Participant.class);
        when(mapper.fromEntity(firstParticipantEntity)).thenReturn(firstExpectedParticipant);
        Participant secondExpectedParticipant = mock(Participant.class);
        when(mapper.fromEntity(secondParticipantEntity)).thenReturn(secondExpectedParticipant);

        List<Participant> participants = courseService.findCourseParticipants(123L);

        assertThat(participants).hasSize(2);
        assertThat(participants.get(0)).isSameAs(firstExpectedParticipant);
        assertThat(participants.get(1)).isSameAs(secondExpectedParticipant);
    }
}
