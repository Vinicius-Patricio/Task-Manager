package task_manager.portifolio.vinicius.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import task_manager.portifolio.vinicius.controller.dto.TaskDTO;
import task_manager.portifolio.vinicius.model.Task;

@Mapper(componentModel= "spring")
public interface TaskMapper {

    @Mapping(target = "status", source = "status")
    @Mapping(target = "priority", source = "priority")
    @Mapping(target = "userId", source = "user.id")
    TaskDTO toDTO(Task task);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "status", source = "status")
    @Mapping(target = "priority", source = "priority")
    @Mapping(target = "description", ignore = true)

    Task toEntity(TaskDTO taskDTO);

}
