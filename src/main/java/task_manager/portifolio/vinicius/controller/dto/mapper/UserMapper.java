package task_manager.portifolio.vinicius.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import task_manager.portifolio.vinicius.controller.dto.UserDTO;
import task_manager.portifolio.vinicius.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "tasks", ignore = true)

    User toEntity(UserDTO dto);

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")

    UserDTO toDTO(User user);
}
