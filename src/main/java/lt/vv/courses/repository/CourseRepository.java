package lt.vv.courses.repository;

import java.util.List;

import lt.vv.courses.repository.entities.CourseEntity;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;

import com.google.common.base.Optional;

public interface CourseRepository extends Repository<CourseEntity, Long> {

	Optional<CourseEntity> findById(long id);

	List<CourseEntity> findAll();

	List<CourseEntity> findAll(Sort sort);

	List<CourseEntity> findByStartTimeAfterAndEndTimeBefore(long startTime, long endTime);

	List<CourseEntity> findByStartTimeAfterOrderByStartTimeAsc(long startTime);

	List<CourseEntity> findByEndTimeBeforeOrderByEndTimeDesc(long endTime);
	
}
