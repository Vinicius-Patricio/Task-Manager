package task_manager.portifolio.vinicius.controller.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "Task")
public record TaskDTO(
    Long id,

    @NotBlank(message = "Titulo é obrigatório")
    @Size(min= 5, max=100, message= "Campo fora do tamanho padrão")
    @Schema(name = "title")
    String title,

    String description,

    @NotBlank(message = "Status é obrigatório")
    @Schema(name = "status")
    String status,

    @NotBlank(message = "Prioridade é obrigatório")
    @Schema(name = "priority")
    String priority,

    LocalDateTime createdAt,

    LocalDate updatedAt,

    @NotNull(message = "Usuario é obrigatório")
    @Schema(name = "user_id")
    Long userId
){}
