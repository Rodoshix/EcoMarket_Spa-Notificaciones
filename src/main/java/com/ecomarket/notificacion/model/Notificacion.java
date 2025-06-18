package com.ecomarket.notificacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "notificaciones")
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Entidad que representa una notificación enviada al usuario.")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la notificación", example = "1")
    private Long id;

    @NotBlank(message = "El destinatario es obligatorio.")
    @Email(message = "Debe ser un correo electrónico válido.")
    @Column(nullable = false, length = 100)
    @Schema(description = "Correo electrónico del destinatario", example = "cliente@correo.com")
    private String destinatario;

    @NotBlank(message = "El mensaje no puede estar vacío.")
    @Size(max = 2000, message = "El mensaje no puede superar los 2000 caracteres.")
    @Column(nullable = false, columnDefinition = "TEXT")
    @Schema(description = "Contenido del mensaje enviado", example = "Tu pedido ha sido confirmado.")
    private String mensaje;

    @NotBlank(message = "El tipo de notificación es obligatorio.")
    @Column(nullable = false, length = 30)
    @Schema(description = "Tipo de notificación (por ejemplo, email, sms)", example = "email")
    private String tipo;

    @Column(name = "fecha_envio")
    @Schema(description = "Fecha y hora en que se envió la notificación", example = "2025-06-17T12:00:00")
    private LocalDateTime fechaEnvio;

    @PrePersist
    protected void asignarFecha() {
        this.fechaEnvio = LocalDateTime.now();
    }
}