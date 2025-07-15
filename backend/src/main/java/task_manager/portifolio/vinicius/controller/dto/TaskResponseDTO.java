package task_manager.portifolio.vinicius.controller.dto;

import java.time.LocalDateTime;

public record TaskResponseDTO(
    Long id,
    String title,
    String description,
    String status,
    String priority,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Long userId,
    String userName
) {
}