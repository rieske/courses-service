package lt.vv.courses.api;

import java.util.List;
import java.util.Optional;

import lt.vv.courses.api.model.Course;
import lt.vv.courses.api.model.CourseNotFound;
import lt.vv.courses.api.model.CsvRecord;
import lt.vv.courses.api.model.Participant;
import lt.vv.courses.services.CoursesService;
import lt.vv.courses.services.CsvMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("courses")
public class CoursesResource {

	@Autowired
	private CoursesService coursesService;

	@Autowired
	private CsvMapper csvMapper;

	@RequestMapping(
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Course> listCourses(
			@RequestParam(required = false) Long fromTime,
			@RequestParam(required = false) Long toTime)
	{
		return coursesService.findCourses(Optional.ofNullable(fromTime), Optional.ofNullable(toTime));
	}

	@RequestMapping(
			value = "{courseId}/participants",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Participant> courseParticipants(@PathVariable long courseId) throws CourseNotFound {
		return coursesService.findCourseParticipants(courseId);
	}

	@RequestMapping(
			value = "{courseId}/participants.csv",
			method = RequestMethod.GET,
			produces = "text/csv;charset=UTF-8")
	public CsvRecord courseParticipantsCsv(@PathVariable long courseId) throws CourseNotFound {
		List<Participant> participants = coursesService.findCourseParticipants(courseId);
		return new CsvRecord("course-" + courseId + "-participants.csv", csvMapper.toCsv(participants));
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Course not found")
	@ExceptionHandler(CourseNotFound.class)
	public void courseNotFound() {
	}
}
