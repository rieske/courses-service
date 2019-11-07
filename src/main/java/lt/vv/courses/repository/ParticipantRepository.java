package lt.vv.courses.repository;

import lt.vv.courses.repository.entities.ParticipantEntity;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ParticipantRepository extends Repository<ParticipantEntity, Long> {

    List<ParticipantEntity> findByCourseId(long courseId);

}
