package lt.vv.courses.integration;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import lt.vv.courses.CoursesApplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = CoursesApplication.class)
@IntegrationTest("server.port:0")
public class CoursesResourceComponentTest {

	@Value("${local.server.port}")
	protected int port;

	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
	}

	@Test
	public void listsAllCourses() {
		// @formatter:off
		when()
			.get("/courses")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("$", hasSize(4))
			.body("[0].id", notNullValue())
			.body("[0].courseName", equalTo("DK course"))
			.body("[1].id", notNullValue())
			.body("[1].courseName", equalTo("EN course"))
			.body("[2].id", notNullValue())
			.body("[2].courseName", equalTo("LT course"))
			.body("[3].id", notNullValue())
			.body("[3].courseName", equalTo("SE course"));
		// @formatter:on
	}

	@Test
	public void listsCoursesAfterGivenFromTime() {
		// @formatter:off
		when()
			.get("/courses?fromTime=2015-01-05T03:00")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("$", hasSize(1))
			.body("[0].id", notNullValue())
			.body("[0].courseName", equalTo("SE course"));
		// @formatter:on
	}

	@Test
	public void listsCoursesBeforeGivenEndTime() {
		// @formatter:off
		when()
			.get("/courses?toTime=2015-01-05T08:00")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("$", hasSize(2))
			.body("[0].id", notNullValue())
			.body("[0].courseName", equalTo("SE course"))
			.body("[1].id", notNullValue())
			.body("[1].courseName", equalTo("EN course"));
		// @formatter:on
	}

	@Test
	public void listsCoursesInGivenTimeframe() throws Exception {
		// @formatter:off
		when()
			.get("/courses?fromTime=2015-01-05T01:59&toTime=2015-01-05T06:00")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("$", hasSize(1))
			.body("[0].id", notNullValue())
			.body("[0].courseName", equalTo("EN course"));
		// @formatter:on
	}

	@Test
	public void returnsNotFoundWhenRequestingParticipantsForNonexistentCourse() {
		// @formatter:off
		when()
			.get("/courses/999/participants")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
		// @formatter:on
	}

	@Test
	public void listsCourseParticipants() {
		// @formatter:off
		when()
			.get("/courses/3/participants")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("$", hasSize(2));
			//.body("[0].firstName", equalTo("Swedish"))
			//.body("[0].lastName", equalTo("Student"))
			//.body("[0].dateOfBirth", equalTo(157755600000L))
			//.body("[1].firstName", equalTo("British"))
			//.body("[1].lastName", equalTo("Student"))
			//.body("[1].dateOfBirth", equalTo(199746000000L));
		// @formatter:on
	}

	@Test
	public void listsCourseParticipantsInCsv() {
		// @formatter:off
		when()
			.get("/courses/3/participants.csv")
		.then()
			.statusCode(HttpStatus.OK.value());
			//.body(equalTo("\"Swedish,Student,157755600000\"\r\n\"British,Student,199746000000\"\r\n"));
		// @formatter:on
	}
}
