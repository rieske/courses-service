package lt.vv.courses.repository;

import java.util.List;

import lt.vv.courses.repository.entities.ParticipantEntity;

import org.springframework.data.repository.Repository;

public interface ParticipantRepository extends Repository<ParticipantEntity, Long> {

	List<ParticipantEntity> findByCourseId(long courseId);

}
