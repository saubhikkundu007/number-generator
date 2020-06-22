package numbergen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import numbergen.repository.JobStatusRepository;

@SpringBootApplication
public class Application {

	@Autowired
	JobStatusRepository jobStatusRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
