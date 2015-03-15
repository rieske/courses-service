package lt.vv.courses.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import lt.vv.courses.CoursesApplication;
import lt.vv.courses.repository.entities.CourseEntity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CoursesApplication.class)
public class CourseRepositoryTest {

	@Autowired
	CourseRepository courseRepository;

	@Test
	public void returnsAbsentOptionalForNonExistentCourse() {
		Optional<CourseEntity> course = courseRepository.findById(9999999999l);

		assertThat(course.isPresent(), is(false));
	}

	@Test
	public void returnsOptionalCourse() {
		Optional<CourseEntity> course = courseRepository.findById(1);

		assertThat(course.isPresent(), is(true));
	}

	@Test
	public void findsAllCourses() {
		List<CourseEntity> allCourses = courseRepository.findAll();

		assertThat(allCourses, hasSize(4));
	}

	@Test
	public void findsAllCoursesSortingByNameAlphabetically() {
		List<CourseEntity> sortedCourses = courseRepository.findAll(new Sort(Direction.ASC, "name"));

		assertThat(sortedCourses, hasSize(4));
		Iterator<CourseEntity> courseIterator = sortedCourses.iterator();
		CourseEntity dkCourse = courseIterator.next();
		assertThat(dkCourse.getName(), equalTo("DK course"));
		CourseEntity enCourse = courseIterator.next();
		assertThat(enCourse.getName(), equalTo("EN course"));
		CourseEntity ltCourse = courseIterator.next();
		assertThat(ltCourse.getName(), equalTo("LT course"));
		CourseEntity seCourse = courseIterator.next();
		assertThat(seCourse.getName(), equalTo("SE course"));
	}

	@Test
	public void findsCoursesStartingAfterGivenTime() {
		List<CourseEntity> courses = courseRepository.findByStartTimeAfterOrderByStartTimeAsc(300L);

		assertThat(courses, hasSize(1));
	}

	@Test
	public void findsCoursesEndingBeforeGivenTime() {
		List<CourseEntity> courses = courseRepository.findByEndTimeBeforeOrderByEndTimeDesc(800L);

		assertThat(courses, hasSize(2));
	}

	@Test
	public void findsCoursesBetweenTimeLimits() {
		List<CourseEntity> courses = courseRepository.findByStartTimeAfterAndEndTimeBefore(199l, 701l);

		assertThat(courses, hasSize(2));
	}
}
