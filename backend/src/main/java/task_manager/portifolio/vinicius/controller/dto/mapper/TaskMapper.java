package task_manager.portifolio.vinicius.controller.dto.mapper;

import org.springframework.stereotype.Component;

import task_manager.portifolio.vinicius.controller.dto.TaskDTO;
import task_manager.portifolio.vinicius.model.Task;

@Component
public class TaskMapper {

    public TaskDTO toDTO(Task task) {
        if (task == null) {
            return null;
        }
        
        try {
            task.getClass().getDeclaredField("id").setAccessible(true);
            task.getClass().getDeclaredField("title").setAccessible(true);
            task.getClass().getDeclaredField("description").setAccessible(true);
            task.getClass().getDeclaredField("status").setAccessible(true);
            task.getClass().getDeclaredField("priority").setAccessible(true);
            task.getClass().getDeclaredField("user").setAccessible(true);
            task.getClass().getDeclaredField("createdAt").setAccessible(true);
            task.getClass().getDeclaredField("updatedAt").setAccessible(true);
            
            return new TaskDTO(
                (Long) task.getClass().getDeclaredField("id").get(task),
                (String) task.getClass().getDeclaredField("title").get(task),
                (String) task.getClass().getDeclaredField("description").get(task),
                (String) task.getClass().getDeclaredField("status").get(task),
                (String) task.getClass().getDeclaredField("priority").get(task),
                (java.time.LocalDateTime) task.getClass().getDeclaredField("createdAt").get(task),
                ((java.time.LocalDateTime) task.getClass().getDeclaredField("updatedAt").get(task)).toLocalDate(),
                task.getClass().getDeclaredField("user").get(task) != null ? 
                    (Long) ((task_manager.portifolio.vinicius.model.User) task.getClass().getDeclaredField("user").get(task)).getClass().getDeclaredField("id").get(task.getClass().getDeclaredField("user").get(task)) : null
            );
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
            throw new RuntimeException("Erro ao mapear Task para TaskDTO", e);
        }
    }

    public Task toEntity(TaskDTO taskDTO) {
        if (taskDTO == null) {
            return null;
        }
        
        Task task = new Task();
        try {
            task.getClass().getDeclaredField("title").setAccessible(true);
            task.getClass().getDeclaredField("status").setAccessible(true);
            task.getClass().getDeclaredField("priority").setAccessible(true);
            
            task.getClass().getDeclaredField("title").set(task, taskDTO.title());
            task.getClass().getDeclaredField("status").set(task, taskDTO.status());
            task.getClass().getDeclaredField("priority").set(task, taskDTO.priority());

        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
            throw new RuntimeException("Erro ao mapear TaskDTO para Task", e);
        }
        return task;
    }
}
