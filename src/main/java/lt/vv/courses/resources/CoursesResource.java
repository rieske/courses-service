package lt.vv.courses.resources;

import lt.vv.courses.api.Course;
import lt.vv.courses.api.CourseNotFound;
import lt.vv.courses.api.CsvRecord;
import lt.vv.courses.api.Participant;
import lt.vv.courses.services.CoursesService;
import lt.vv.courses.services.CsvMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("courses")
public class CoursesResource {

    private final CoursesService coursesService;
    private final CsvMapper csvMapper;

    public CoursesResource(CoursesService coursesService, CsvMapper csvMapper) {
        this.coursesService = coursesService;
        this.csvMapper = csvMapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Course> listCourses(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime fromTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime toTime) {
        return coursesService.findCourses(Optional.ofNullable(fromTime), Optional.ofNullable(toTime));
    }

    @GetMapping(value = "{courseId}/participants", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Participant> courseParticipants(@PathVariable long courseId) throws CourseNotFound {
        return coursesService.findCourseParticipants(courseId);
    }

    @GetMapping(value = "{courseId}/participants.csv", produces = "text/csv;charset=UTF-8")
    public CsvRecord courseParticipantsCsv(@PathVariable long courseId) throws CourseNotFound {
        List<Participant> participants = coursesService.findCourseParticipants(courseId);
        return new CsvRecord("course-" + courseId + "-participants.csv", csvMapper.toCsv(participants));
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Course not found")
    @ExceptionHandler(CourseNotFound.class)
    public void courseNotFound() {
    }
}
