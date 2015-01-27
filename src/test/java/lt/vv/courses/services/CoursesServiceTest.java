package lt.vv.courses.services;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import lt.vv.courses.api.model.Course;
import lt.vv.courses.api.model.CourseNotFound;
import lt.vv.courses.api.model.Participant;
import lt.vv.courses.repository.CourseRepository;
import lt.vv.courses.repository.ParticipantRepository;
import lt.vv.courses.repository.entities.CourseEntity;
import lt.vv.courses.repository.entities.ParticipantEntity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class CoursesServiceTest {

	@Mock
	CourseRepository courseRepository;

	@Mock
	EntityToApiModelMapper mapper;

	@Mock
	ParticipantRepository participantRepository;

	@InjectMocks
	CoursesService courseService;

	@Test
	public void findsAllCourses() {
		CourseEntity firstCourseEntity = mock(CourseEntity.class);
		CourseEntity secondCourseEntity = mock(CourseEntity.class);
		when(courseRepository.findAll(new Sort(Direction.ASC, "name"))).thenReturn(
				Lists.newArrayList(firstCourseEntity, secondCourseEntity));
		Course firstExpectedCourse = mock(Course.class);
		when(mapper.fromEntity(firstCourseEntity)).thenReturn(firstExpectedCourse);
		Course secondExpectedCourse = mock(Course.class);
		when(mapper.fromEntity(secondCourseEntity)).thenReturn(secondExpectedCourse);

		List<Course> courses = courseService.findCourses(Optional.<Long> absent(), Optional.<Long> absent());

		assertThat(courses, hasSize(2));
		assertThat(courses.get(0), sameInstance(firstExpectedCourse));
		assertThat(courses.get(1), sameInstance(secondExpectedCourse));
	}

	@Test
	public void findsCoursesAfterGivenStartTime() {
		CourseEntity firstCourseEntity = mock(CourseEntity.class);
		CourseEntity secondCourseEntity = mock(CourseEntity.class);
		when(courseRepository.findByStartTimeAfterOrderByStartTimeAsc(100L)).thenReturn(
				Lists.newArrayList(firstCourseEntity, secondCourseEntity));
		Course firstExpectedCourse = mock(Course.class);
		when(mapper.fromEntity(firstCourseEntity)).thenReturn(firstExpectedCourse);
		Course secondExpectedCourse = mock(Course.class);
		when(mapper.fromEntity(secondCourseEntity)).thenReturn(secondExpectedCourse);

		List<Course> courses = courseService.findCourses(Optional.of(100L), Optional.<Long> absent());

		assertThat(courses, hasSize(2));
		assertThat(courses.get(0), sameInstance(firstExpectedCourse));
		assertThat(courses.get(1), sameInstance(secondExpectedCourse));
	}

	@Test
	public void findsCoursesBeforeGivenEndTime() {
		CourseEntity firstCourseEntity = mock(CourseEntity.class);
		CourseEntity secondCourseEntity = mock(CourseEntity.class);
		when(courseRepository.findByEndTimeBeforeOrderByEndTimeDesc(200L)).thenReturn(
				Lists.newArrayList(firstCourseEntity, secondCourseEntity));
		Course firstExpectedCourse = mock(Course.class);
		when(mapper.fromEntity(firstCourseEntity)).thenReturn(firstExpectedCourse);
		Course secondExpectedCourse = mock(Course.class);
		when(mapper.fromEntity(secondCourseEntity)).thenReturn(secondExpectedCourse);

		List<Course> courses = courseService.findCourses(Optional.<Long> absent(), Optional.of(200L));

		assertThat(courses, hasSize(2));
		assertThat(courses.get(0), sameInstance(firstExpectedCourse));
		assertThat(courses.get(1), sameInstance(secondExpectedCourse));
	}

	@Test
	public void findsCoursesWithinGivenTimeframe() {
		CourseEntity firstCourseEntity = mock(CourseEntity.class);
		CourseEntity secondCourseEntity = mock(CourseEntity.class);
		when(courseRepository.findByStartTimeAfterAndEndTimeBefore(100L, 200L)).thenReturn(
				Lists.newArrayList(firstCourseEntity, secondCourseEntity));
		Course firstExpectedCourse = mock(Course.class);
		when(mapper.fromEntity(firstCourseEntity)).thenReturn(firstExpectedCourse);
		Course secondExpectedCourse = mock(Course.class);
		when(mapper.fromEntity(secondCourseEntity)).thenReturn(secondExpectedCourse);

		List<Course> courses = courseService.findCourses(Optional.of(100L), Optional.of(200L));

		assertThat(courses, hasSize(2));
		assertThat(courses.get(0), sameInstance(firstExpectedCourse));
		assertThat(courses.get(1), sameInstance(secondExpectedCourse));
	}

	@Test(expected = CourseNotFound.class)
	public void throwsCourseNotFoundForNonexistentCourseParticipantSearch() throws CourseNotFound {
		when(courseRepository.findById(999L)).thenReturn(Optional.<CourseEntity> absent());

		courseService.findCourseParticipants(999L);
	}

	@Test
	public void findsCourseParticipants() throws CourseNotFound {
		when(courseRepository.findById(123L)).thenReturn(Optional.of(mock(CourseEntity.class)));
		ParticipantEntity firstParticipantEntity = mock(ParticipantEntity.class);
		ParticipantEntity secondParticipantEntity = mock(ParticipantEntity.class);
		when(participantRepository.findByCourseId(123L)).thenReturn(
				Lists.newArrayList(firstParticipantEntity, secondParticipantEntity));
		Participant firstExpectedParticipant = mock(Participant.class);
		when(mapper.fromEntity(firstParticipantEntity)).thenReturn(firstExpectedParticipant);
		Participant secondExpectedParticipant = mock(Participant.class);
		when(mapper.fromEntity(secondParticipantEntity)).thenReturn(secondExpectedParticipant);

		List<Participant> participants = courseService.findCourseParticipants(123L);

		assertThat(participants, hasSize(2));
		assertThat(participants.get(0), sameInstance(firstExpectedParticipant));
		assertThat(participants.get(1), sameInstance(secondExpectedParticipant));
	}
}
