package task_manager.portifolio.vinicius.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        User user = userRepository.findById(taskDTO.userId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        existingTask.setTitle(taskDTO.title());
        existingTask.setDescription(taskDTO.description());
        existingTask.setStatus(taskDTO.status());
        existingTask.setPriority(taskDTO.priority());
        existingTask.setUser(user);

        Task updatedTask = taskRepository.save(existingTask);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Tarefa não encontrada");
        }
        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        List<Task> tasks = taskRepository.findByUser(user);
        return ResponseEntity.ok(tasks);
    }

}
