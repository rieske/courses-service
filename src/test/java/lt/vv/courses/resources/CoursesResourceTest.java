package lt.vv.courses.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import lt.vv.courses.api.Course;
import lt.vv.courses.api.CourseNotFound;
import lt.vv.courses.api.Participant;
import lt.vv.courses.converters.CsvMessageConverter;
import lt.vv.courses.services.CoursesService;
import lt.vv.courses.services.CsvMapper;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CoursesResourceTest {

	@Mock
	CoursesService coursesService;

	@Mock
	CsvMapper csvMapper;

	@InjectMocks
	CoursesResource coursesResource;

	ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

	@Before
	public void setUpMockMvc() {
		RestAssuredMockMvc.standaloneSetup(coursesResource);
		RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	@Test
	public void listsAllCoursesWihoutQueryParameters() throws JsonProcessingException {
		List<Course> courses = List.of(new Course(
				1,
				"name",
				Instant.parse("2015-01-03T10:15:00.00Z"),
				Instant.parse("2015-01-03T10:18:00.00Z"),
				"location"));
		when(coursesService.findCourses(Optional.empty(), Optional.empty()))
				.thenReturn(courses);

		// @formatter:off
		when().
			get("/courses").
		then().
			statusCode(HttpStatus.SC_OK).
			expect(content().json(mapper.writeValueAsString(courses)));
		// @formatter:on

		verify(coursesService).findCourses(Optional.empty(), Optional.empty());
		verifyNoMoreInteractions(coursesService);
	}

	@Test
	public void listsCoursesAfterGivenFromTime() throws JsonProcessingException {
		List<Course> courses = List.of(new Course(
				1,
				"name",
				Instant.parse("2015-01-03T10:15:00.00Z"),
				Instant.parse("2015-01-03T10:18:00.00Z"),
				"location"));
		when(coursesService.findCourses(Optional.of(LocalDateTime.parse("2015-01-03T10:15")), Optional.empty()))
				.thenReturn(courses);

		// @formatter:off
		given().
			param("fromTime", "2015-01-03T10:15").
		when().
			get("/courses").
		then().
			statusCode(HttpStatus.SC_OK).
			expect(content().json(mapper.writeValueAsString(courses)));
		// @formatter:on

		verify(coursesService).findCourses(Optional.of(LocalDateTime.parse("2015-01-03T10:15")), Optional.empty());
		verifyNoMoreInteractions(coursesService);
	}

	@Test
	public void listsCoursesBeforeGivenEndTime() throws JsonProcessingException {
		List<Course> courses = List.of(new Course(
				1,
				"name",
				Instant.parse("2015-01-03T10:15:00.00Z"),
				Instant.parse("2015-01-03T10:18:00.00Z"),
				"location"));
		when(coursesService.findCourses(Optional.empty(), Optional.of(LocalDateTime.parse("2015-01-03T10:18"))))
				.thenReturn(courses);

		// @formatter:off
		given().
			param("toTime", "2015-01-03T10:18").
		when().
			get("/courses").
		then().
			statusCode(HttpStatus.SC_OK).
			expect(content().json(mapper.writeValueAsString(courses)));
		// @formatter:on

		verify(coursesService).findCourses(Optional.empty(), Optional.of(LocalDateTime.parse("2015-01-03T10:18")));
		verifyNoMoreInteractions(coursesService);
	}

	@Test
	public void listsCoursesInGivenTimeframe() throws JsonProcessingException {
		List<Course> courses = List.of(new Course(
				1,
				"name",
				Instant.parse("2015-01-03T10:15:00.00Z"),
				Instant.parse("2015-01-03T10:18:00.00Z"),
				"location"));
		when(coursesService.findCourses(
				Optional.of(LocalDateTime.parse("2015-01-03T00:00:00")),
				Optional.of(LocalDateTime.parse("2015-01-05T00:00:00"))))
						.thenReturn(courses);

		// @formatter:off
		given().
			param("fromTime", "2015-01-03T00:00").
			param("toTime", "2015-01-05T00:00").
		when().
			get("/courses").
		then().
			statusCode(HttpStatus.SC_OK).
			expect(content().json(mapper.writeValueAsString(courses)));
		// @formatter:on

		verify(coursesService).findCourses(Optional.of(LocalDateTime.parse("2015-01-03T00:00")),
				Optional.of(LocalDateTime.parse("2015-01-05T00:00")));
		verifyNoMoreInteractions(coursesService);
	}

	@Test
	public void returnsNotFoundWhenRequestingParticipantsForNonexistentCourse() throws CourseNotFound {
		when(coursesService.findCourseParticipants(999L)).thenThrow(new CourseNotFound());

		// @formatter:off
		when().
			get("/courses/999/participants").
		then().
			statusCode(HttpStatus.SC_NOT_FOUND);
		// @formatter:on
	}

	@Test
	public void returnsCourseParticipants() throws Exception {
		when(coursesService.findCourseParticipants(123L))
				.thenReturn(List.of(new Participant("name", "surname", 999)));

		// @formatter:off
		when().
			get("/courses/123/participants").
		then().
			statusCode(HttpStatus.SC_OK).
			expect(content().json("[{\"firstName\":\"name\",\"lastName\":\"surname\",\"dateOfBirth\":999}]"));
		// @formatter:on
	}

	@Test
	public void returnsNotFoundWhenRequestingParticipantsForNonexistentCourseInCsv() throws Exception {
		when(coursesService.findCourseParticipants(999L)).thenThrow(new CourseNotFound());

		MockMvc mvc = MockMvcBuilders
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
		List<Participant> participants = List.of(new Participant("name", "surname", 999));
		when(coursesService.findCourseParticipants(123L)).thenReturn(participants);
		when(csvMapper.toCsv(participants)).thenReturn(Arrays.asList("aaa", "bbb"));

		MockMvc mvc = MockMvcBuilders
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
