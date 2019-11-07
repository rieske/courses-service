package lt.vv.courses.repository;

import lt.vv.courses.CoursesApplication;
import lt.vv.courses.repository.entities.CourseEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CoursesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void returnsAbsentOptionalForNonExistentCourse() {
        Optional<CourseEntity> course = courseRepository.findById(9999999999L);

        assertThat(course.isPresent()).isFalse();
    }

    @Test
    public void returnsOptionalCourse() {
        Optional<CourseEntity> course = courseRepository.findById(1);

        assertThat(course.isPresent()).isTrue();
    }

    @Test
    public void findsAllCourses() {
        List<CourseEntity> allCourses = courseRepository.findAll();

        assertThat(allCourses).hasSize(4);
    }

    @Test
    public void findsAllCoursesSortingByNameAlphabetically() {
        List<CourseEntity> sortedCourses = courseRepository.findAll(Sort.by(Direction.ASC, "name"));

        assertThat(sortedCourses).hasSize(4);
        Iterator<CourseEntity> courseIterator = sortedCourses.iterator();
        CourseEntity dkCourse = courseIterator.next();
        assertThat(dkCourse.getName()).isEqualTo("DK course");
        CourseEntity enCourse = courseIterator.next();
        assertThat(enCourse.getName()).isEqualTo("EN course");
        CourseEntity ltCourse = courseIterator.next();
        assertThat(ltCourse.getName()).isEqualTo("LT course");
        CourseEntity seCourse = courseIterator.next();
        assertThat(seCourse.getName()).isEqualTo("SE course");
    }

    @Test
    public void findsCoursesStartingAfterGivenTime() {
        List<CourseEntity> courses = courseRepository.findByStartTimeAfterOrderByStartTimeAsc(
                Timestamp.valueOf(LocalDateTime.parse("2015-01-05T03:00:00")));

        assertThat(courses).hasSize(1);
    }

    @Test
    public void findsCoursesEndingBeforeGivenTime() {
        List<CourseEntity> courses = courseRepository.findByEndTimeBeforeOrderByEndTimeDesc(
                Timestamp.valueOf(LocalDateTime.parse("2015-01-05T08:00:00")));

        assertThat(courses).hasSize(2);
    }

    @Test
    public void findsCoursesBetweenTimeLimits() {
        List<CourseEntity> courses = courseRepository.findByStartTimeAfterAndEndTimeBefore(
                Timestamp.valueOf(LocalDateTime.parse("2015-01-05T01:59:00")),
                Timestamp.valueOf(LocalDateTime.parse("2015-01-05T07:01:00")));

        assertThat(courses).hasSize(2);
    }
}
