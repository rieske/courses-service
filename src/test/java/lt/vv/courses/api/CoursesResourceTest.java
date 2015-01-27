package lt.vv.courses.api;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import lt.vv.courses.api.model.Course;
import lt.vv.courses.api.model.CourseNotFound;
import lt.vv.courses.api.model.Participant;
import lt.vv.courses.converters.CsvMessageConverter;
import lt.vv.courses.services.CoursesService;
import lt.vv.courses.services.CsvMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class CoursesResourceTest {

	@Mock
	CoursesService coursesService;

	@Mock
	CsvMapper csvMapper;

	@InjectMocks
	CoursesResource coursesResource;

	MockMvc mvc;

	@Before
	public void setUpMockMvc() {
		// @formatter:off
		mvc = MockMvcBuilders
					.standaloneSetup(coursesResource)
					.build();
		// @formatter:on
	}

	@Test
	public void listsAllCoursesWihoutQueryParameters() throws Exception {
		when(coursesService.findCourses(Optional.<Long> absent(), Optional.<Long> absent()))
				.thenReturn(Lists.newArrayList(new Course(1, "name", 1, 2, "location")));

		// @formatter:off
		mvc.perform(
				get("/courses"))
				.andExpect(status().isOk())
				.andExpect(content().json("[{\"id\":1,\"courseName\":\"name\",\"startTime\":1,\"endTime\":2,\"location\":\"location\"}]"));
		// @formatter:on

		verify(coursesService).findCourses(Optional.<Long> absent(), Optional.<Long> absent());
		verifyNoMoreInteractions(coursesService);
	}

	@Test
	public void listsCoursesAfterGivenFromTime() throws Exception {
		when(coursesService.findCourses(Optional.of(10L), Optional.<Long> absent()))
				.thenReturn(Lists.newArrayList(new Course(1, "name", 1, 2, "location")));

		// @formatter:off
		mvc.perform(
				get("/courses").param("fromTime", "10"))
				.andExpect(status().isOk())
				.andExpect(content().json("[{\"id\":1,\"courseName\":\"name\",\"startTime\":1,\"endTime\":2,\"location\":\"location\"}]"));
		// @formatter:on

		verify(coursesService).findCourses(Optional.of(10L), Optional.<Long> absent());
		verifyNoMoreInteractions(coursesService);
	}

	@Test
	public void listsCoursesBeforeGivenEndTime() throws Exception {
		when(coursesService.findCourses(Optional.<Long> absent(), Optional.of(20L)))
				.thenReturn(Lists.newArrayList(new Course(1, "name", 1, 2, "location")));

		// @formatter:off
		mvc.perform(
				get("/courses").param("toTime", "20"))
				.andExpect(status().isOk())
				.andExpect(content().json("[{\"id\":1,\"courseName\":\"name\",\"startTime\":1,\"endTime\":2,\"location\":\"location\"}]"));
		// @formatter:on

		verify(coursesService).findCourses(Optional.<Long> absent(), Optional.of(20L));
		verifyNoMoreInteractions(coursesService);
	}

	@Test
	public void listsCoursesInGivenTimeframe() throws Exception {
		when(coursesService.findCourses(Optional.of(10L), Optional.of(20L)))
				.thenReturn(Lists.newArrayList(new Course(1, "name", 1, 2, "location")));

		// @formatter:off
		mvc.perform(
				get("/courses").param("fromTime", "10").param("toTime", "20"))
				.andExpect(status().isOk())
				.andExpect(content().json("[{\"id\":1,\"courseName\":\"name\",\"startTime\":1,\"endTime\":2,\"location\":\"location\"}]"));
		// @formatter:on

		verify(coursesService).findCourses(Optional.of(10L), Optional.of(20L));
		verifyNoMoreInteractions(coursesService);
	}

	@Test
	public void returnsNotFoundWhenRequestingParticipantsForNonexistentCourse() throws Exception {
		when(coursesService.findCourseParticipants(999L)).thenThrow(new CourseNotFound());

		// @formatter:off
		mvc.perform(
				get("/courses/999/participants"))
				.andExpect(status().isNotFound());
		// @formatter:on
	}

	@Test
	public void returnsCourseParticipants() throws Exception {
		when(coursesService.findCourseParticipants(123L)).thenReturn(Lists.newArrayList(new Participant("name", "surname", 999)));

		// @formatter:off
		mvc.perform(
				get("/courses/123/participants"))
				.andExpect(status().isOk())
				.andExpect(content().json("[{\"firstName\":\"name\",\"lastName\":\"surname\",\"dateOfBirth\":999,}]"));
		// @formatter:on
	}

	@Test
	public void returnsNotFoundWhenRequestingParticipantsForNonexistentCourseInCsv() throws Exception {
		when(coursesService.findCourseParticipants(999L)).thenThrow(new CourseNotFound());

		mvc = MockMvcBuilders
				.standaloneSetup(coursesResource).setMessageConverters(new CsvMessageConverter())
				.build();

		// @formatter:off
		mvc.perform(
				get("/courses/999/participants.csv"))
				.andExpect(status().isNotFound());
		// @formatter:on
	}

	@Test
	public void returnsCourseParticipantsInCsv() throws Exception {
		List<Participant> participants = Lists.newArrayList(new Participant("name", "surname", 999));
		when(coursesService.findCourseParticipants(123L)).thenReturn(participants);
		when(csvMapper.toCsv(participants)).thenReturn(Lists.newArrayList("aaa", "bbb"));

		mvc = MockMvcBuilders
				.standaloneSetup(coursesResource).setMessageConverters(new CsvMessageConverter())
				.build();

		// @formatter:off
		mvc.perform(
				get("/courses/123/participants.csv"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/csv;charset=UTF-8"))
				.andExpect(header().string("Content-Disposition", equalTo("attachment; filename=\"course-123-participants.csv\"")))
				.andExpect(content().string(equalTo("aaa\r\nbbb\r\n")));
		// @formatter:on
	}
}
