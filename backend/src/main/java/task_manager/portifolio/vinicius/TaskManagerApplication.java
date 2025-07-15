package task_manager.portifolio.vinicius;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableJpaRepositories("task_manager.portifolio.vinicius.repository")
@EntityScan("task_manager.portifolio.vinicius.model")
public class TaskManagerApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
		.load();

		dotenv.entries().forEach(entry ->{
			System.setProperty(entry.getKey(), entry.getValue());
		});
		SpringApplication.run(TaskManagerApplication.class, args);
	}

}
