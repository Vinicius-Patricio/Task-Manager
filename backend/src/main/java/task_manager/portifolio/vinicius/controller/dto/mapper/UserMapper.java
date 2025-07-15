package task_manager.portifolio.vinicius.controller.dto.mapper;

import org.springframework.stereotype.Component;

import task_manager.portifolio.vinicius.controller.dto.UserDTO;
import task_manager.portifolio.vinicius.model.User;

@Component
public class UserMapper {

    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        
        User user = new User();
        try {
            // Usando reflexão para definir os campos
            user.getClass().getDeclaredField("name").setAccessible(true);
            user.getClass().getDeclaredField("email").setAccessible(true);
            user.getClass().getDeclaredField("password").setAccessible(true);
            
            user.getClass().getDeclaredField("name").set(user, dto.name());
            user.getClass().getDeclaredField("email").set(user, dto.email());
            user.getClass().getDeclaredField("password").set(user, dto.password());
            
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
            throw new RuntimeException("Erro ao mapear UserDTO para User", e);
        }
        return user;
    }

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        
        // Usando reflexão para acessar os campos
        try {
            user.getClass().getDeclaredField("id").setAccessible(true);
            user.getClass().getDeclaredField("name").setAccessible(true);
            user.getClass().getDeclaredField("email").setAccessible(true);
            user.getClass().getDeclaredField("password").setAccessible(true);
            user.getClass().getDeclaredField("createdAt").setAccessible(true);
            user.getClass().getDeclaredField("updatedAt").setAccessible(true);
            
            return new UserDTO(
                (Long) user.getClass().getDeclaredField("id").get(user),
                (String) user.getClass().getDeclaredField("name").get(user),
                (String) user.getClass().getDeclaredField("email").get(user),
                (String) user.getClass().getDeclaredField("password").get(user),
                (java.time.LocalDateTime) user.getClass().getDeclaredField("createdAt").get(user),
                (java.time.LocalDateTime) user.getClass().getDeclaredField("updatedAt").get(user)
            );
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Erro ao mapear User para UserDTO", e);
        }
    }
}
