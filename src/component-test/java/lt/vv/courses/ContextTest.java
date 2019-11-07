package lt.vv.courses;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CoursesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContextTest {

	@Test
	public void contextStarts() {
	}

}
