package com.ecomarket.notificacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "DTO utilizado para enviar una notificación por correo electrónico.")
public class NotificacionDTO {

    @NotBlank(message = "El destinatario es obligatorio.")
    @Email(message = "Debe ser un correo electrónico válido.")
    @Schema(description = "Correo del destinatario", example = "cliente@correo.com", required = true)
    private String destinatario;

    @NotBlank(message = "El asunto no puede estar vacío.")
    @Size(max = 100, message = "El asunto no puede tener más de 100 caracteres.")
    @Schema(description = "Asunto del correo", example = "Confirmación de pedido", required = true)
    private String asunto;

    @NotBlank(message = "El cuerpo del mensaje es obligatorio.")
    @Size(max = 2000, message = "El cuerpo no puede tener más de 2000 caracteres.")
    @Schema(description = "Mensaje que se enviará por correo", example = "Tu pedido ha sido confirmado.", required = true)
    private String cuerpo;
}