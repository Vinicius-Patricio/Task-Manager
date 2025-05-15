package task_manager.portifolio.vinicius.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import task_manager.portifolio.vinicius.controller.dto.TaskDTO;
import task_manager.portifolio.vinicius.model.Task;
import task_manager.portifolio.vinicius.model.User;
import task_manager.portifolio.vinicius.repository.TaskRepository;
import task_manager.portifolio.vinicius.repository.UserRepository;


@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {


    private final TaskRepository taskRepository;
    private final UserRepository userRepository ;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {

        User user = userRepository.findById(taskDTO.userId())
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Task task = new Task();
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setStatus(taskDTO.priority());
        task.setPriority(taskDTO.status());
        task.setUser(user);

        Task savedTask = taskRepository.save(task);
        return ResponseEntity.ok(savedTask);

    }
    

}
