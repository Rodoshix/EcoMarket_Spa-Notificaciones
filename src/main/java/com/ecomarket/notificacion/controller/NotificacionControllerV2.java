package com.ecomarket.notificacion.controller;

import com.ecomarket.notificacion.assembler.NotificacionModelAssembler;
import com.ecomarket.notificacion.model.Notificacion;
import com.ecomarket.notificacion.repository.NotificacionRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Notificaciones v2", description = "Versión HATEOAS de la API de notificaciones")
@RestController
@RequestMapping("/api/v2/notificaciones")
public class NotificacionControllerV2 {

    @Autowired
    private NotificacionRepository repository;

    @Autowired
    private NotificacionModelAssembler assembler;

    @Operation(summary = "Obtener todas las notificaciones con enlaces HATEOAS")
    @ApiResponse(responseCode = "200", description = "Lista de notificaciones con enlaces")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Notificacion>>> obtenerTodas() {
        List<Notificacion> lista = repository.findAll();

        List<EntityModel<Notificacion>> modelos = lista.stream()
                .map(assembler::toModel)
                .toList();

        CollectionModel<EntityModel<Notificacion>> collection = CollectionModel.of(
            modelos,
            linkTo(methodOn(NotificacionControllerV2.class).obtenerTodas()).withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    @Operation(summary = "Obtener notificación por ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificación encontrada"),
        @ApiResponse(responseCode = "404", description = "Notificación no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Notificacion>> obtenerPorId(
        @Parameter(description = "ID de la notificación", example = "1")
        @PathVariable Long id
    ) {
        Optional<Notificacion> notificacion = repository.findById(id);
        return notificacion
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener notificaciones por destinatario con enlaces HATEOAS")
    @ApiResponse(responseCode = "200", description = "Lista filtrada por destinatario")
    @GetMapping("/destinatario/{correo}")
    public ResponseEntity<CollectionModel<EntityModel<Notificacion>>> obtenerPorDestinatario(
        @Parameter(description = "Correo del destinatario", example = "cliente@correo.com")
        @PathVariable String correo
    ) {
        List<Notificacion> lista = repository.findByDestinatario(correo);
        List<EntityModel<Notificacion>> modelos = lista.stream()
                .map(assembler::toModel)
                .toList();

        CollectionModel<EntityModel<Notificacion>> collection = CollectionModel.of(
            modelos,
            linkTo(methodOn(NotificacionControllerV2.class).obtenerPorDestinatario(correo)).withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }

    @Operation(summary = "Obtener notificaciones por tipo con enlaces HATEOAS")
    @ApiResponse(responseCode = "200", description = "Lista filtrada por tipo")
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<CollectionModel<EntityModel<Notificacion>>> obtenerPorTipo(
        @Parameter(description = "Tipo de notificación", example = "email")
        @PathVariable String tipo
    ) {
        List<Notificacion> lista = repository.findByTipo(tipo);
        List<EntityModel<Notificacion>> modelos = lista.stream()
                .map(assembler::toModel)
                .toList();

        CollectionModel<EntityModel<Notificacion>> collection = CollectionModel.of(
            modelos,
            linkTo(methodOn(NotificacionControllerV2.class).obtenerPorTipo(tipo)).withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }
}