package lt.vv.courses.repository;

import lt.vv.courses.CoursesApplication;
import lt.vv.courses.repository.entities.ParticipantEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CoursesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParticipantRepositoryTest {

	@Autowired
	ParticipantRepository participantRepository;

	@Test
	public void returnsEmptyListForACourseWithNoParticipants() {
		List<ParticipantEntity> participants = participantRepository.findByCourseId(4);

		assertThat(participants).isEmpty();
	}

	@Test
	public void returnsEmptyListForNonExistentCourse() {
		List<ParticipantEntity> participants = participantRepository.findByCourseId(999);

		assertThat(participants).isEmpty();
	}

	@Test
	public void returnsCourseParticipants() {
		List<ParticipantEntity> participants = participantRepository.findByCourseId(3);

		assertThat(participants).hasSize(2);
		assertThat(participants.get(0).getFirstName()).isEqualTo("Swedish");
		assertThat(participants.get(0).getLastName()).isEqualTo("Student");
		assertThat(participants.get(1).getFirstName()).isEqualTo("British");
		assertThat(participants.get(1).getLastName()).isEqualTo("Student");
	}

}
