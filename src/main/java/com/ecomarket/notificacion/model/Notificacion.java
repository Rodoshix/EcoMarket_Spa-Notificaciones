package com.ecomarket.notificacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Getter
@Setter
@NoArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El destinatario es obligatorio.")
    @Email(message = "Debe ser un correo electrónico válido.")
    @Column(nullable = false, length = 100)
    private String destinatario;

    @NotBlank(message = "El mensaje no puede estar vacío.")
    @Size(max = 2000, message = "El mensaje no puede superar los 2000 caracteres.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @NotBlank(message = "El tipo de notificación es obligatorio.")
    @Column(nullable = false, length = 30)
    private String tipo;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @PrePersist
    protected void asignarFecha() {
        this.fechaEnvio = LocalDateTime.now();
    }
}