package lt.vv.courses.repository;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.List;

import lt.vv.courses.CoursesApplication;
import lt.vv.courses.repository.entities.ParticipantEntity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CoursesApplication.class)
public class ParticipantRepositoryTest {

	@Autowired
	ParticipantRepository participantRepository;

	@Test
	public void returnsEmptyListForACourseWithNoParticipants() {
		List<ParticipantEntity> participants = participantRepository.findByCourseId(4);

		assertThat(participants, empty());
	}

	@Test
	public void returnsEmptyListForNonExistentCourse() {
		List<ParticipantEntity> participants = participantRepository.findByCourseId(999);

		assertThat(participants, empty());
	}

	@Test
	public void returnsCourseParticipants() {
		List<ParticipantEntity> participants = participantRepository.findByCourseId(3);

		assertThat(participants, hasSize(2));
		assertThat(participants.get(0).getFirstName(), equalTo("Swedish"));
		assertThat(participants.get(0).getLastName(), equalTo("Student"));
		assertThat(participants.get(1).getFirstName(), equalTo("British"));
		assertThat(participants.get(1).getLastName(), equalTo("Student"));
	}

}
