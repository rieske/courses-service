package lt.vv.courses.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import lt.vv.courses.repository.entities.CourseEntity;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;

public interface CourseRepository extends Repository<CourseEntity, Long> {

	Optional<CourseEntity> findById(long id);

	List<CourseEntity> findAll();

	List<CourseEntity> findAll(Sort sort);

	List<CourseEntity> findByStartTimeAfterAndEndTimeBefore(Timestamp startTime, Timestamp endTime);

	List<CourseEntity> findByStartTimeAfterOrderByStartTimeAsc(Timestamp startTime);

	List<CourseEntity> findByEndTimeBeforeOrderByEndTimeDesc(Timestamp endTime);
	
}
