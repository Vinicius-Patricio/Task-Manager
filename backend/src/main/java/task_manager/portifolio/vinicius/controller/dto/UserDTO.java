package task_manager.portifolio.vinicius.controller.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "User")
public record UserDTO(
    Long id,
    
    @NotBlank(message= "Nome é obrigatório")
    @Size(min= 2, max=100, message= "Campo fora do tamanho padrão")
    @Schema(name = "name")
    String name,

    @NotBlank(message= "E-mail é obrigatório")
    @Size(min= 2, max=500, message= "Campo fora do tamanho padrão")
    @Schema(name = "email")
    String email,

    @Schema(description = "Senha criptografada (apenas para testes)",
    accessMode = Schema.AccessMode.WRITE_ONLY)
    String password,

    LocalDateTime createdAt,
    
    LocalDateTime updatedAt

){}
