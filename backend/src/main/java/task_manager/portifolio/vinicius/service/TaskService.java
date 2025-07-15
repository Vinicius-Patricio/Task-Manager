package task_manager.portifolio.vinicius.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import task_manager.portifolio.vinicius.model.Task;
import task_manager.portifolio.vinicius.repository.TaskRepository;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task task) {
        Task existingTask = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        existingTask.setPriority(task.getPriority());
        
        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        
        taskRepository.delete(task);
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }
}
