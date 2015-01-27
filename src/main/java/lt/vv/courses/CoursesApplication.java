package lt.vv.courses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("lt.vv.courses")
@EnableAutoConfiguration
public class CoursesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoursesApplication.class);
	}

}
