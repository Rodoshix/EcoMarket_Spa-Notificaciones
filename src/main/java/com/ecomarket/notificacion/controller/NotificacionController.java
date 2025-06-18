package com.ecomarket.notificacion.controller;

import com.ecomarket.notificacion.dto.NotificacionDTO;
import com.ecomarket.notificacion.model.Notificacion;
import com.ecomarket.notificacion.repository.NotificacionRepository;
import com.ecomarket.notificacion.services.NotificacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Notificaciones", description = "Operaciones relacionadas con el envío y consulta de notificaciones")
@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Operation(summary = "Enviar notificación por correo y guardar en base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Correo enviado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error al enviar correo", content = @Content)
    })
    @PostMapping("/enviar")
    public ResponseEntity<String> enviar(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la notificación a enviar",
            required = true,
            content = @Content(schema = @Schema(implementation = NotificacionDTO.class))
        )
        @RequestBody NotificacionDTO dto
    ) {
        notificacionService.enviarCorreo(dto);
        return ResponseEntity.ok("Correo enviado correctamente");
    }

    @Operation(summary = "Obtener todas las notificaciones registradas")
    @ApiResponse(responseCode = "200", description = "Lista de notificaciones")
    @GetMapping
    public ResponseEntity<List<Notificacion>> obtenerTodas() {
        List<Notificacion> notificaciones = notificacionRepository.findAll();
        return ResponseEntity.ok(notificaciones);
    }

    @Operation(summary = "Buscar notificaciones por correo del destinatario")
    @ApiResponse(responseCode = "200", description = "Lista filtrada por destinatario")
    @GetMapping("/destinatario/{correo}")
    public ResponseEntity<List<Notificacion>> obtenerPorDestinatario(
        @Parameter(description = "Correo del destinatario", example = "cliente@correo.com")
        @PathVariable String correo
    ) {
        List<Notificacion> resultado = notificacionRepository.findByDestinatario(correo);
        return ResponseEntity.ok(resultado);
    }

    @Operation(summary = "Buscar notificaciones por tipo")
    @ApiResponse(responseCode = "200", description = "Lista filtrada por tipo")
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Notificacion>> obtenerPorTipo(
        @Parameter(description = "Tipo de notificación", example = "email")
        @PathVariable String tipo
    ) {
        List<Notificacion> resultado = notificacionRepository.findByTipo(tipo);
        return ResponseEntity.ok(resultado);
    }
}