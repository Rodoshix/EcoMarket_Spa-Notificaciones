package com.ecomarket.notificacion.assembler;

import com.ecomarket.notificacion.model.Notificacion;
import com.ecomarket.notificacion.controller.NotificacionController;
import com.ecomarket.notificacion.controller.NotificacionControllerV2;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class NotificacionModelAssembler implements RepresentationModelAssembler<Notificacion, EntityModel<Notificacion>> {

    @Override
    public EntityModel<Notificacion> toModel(Notificacion notificacion) {
        return EntityModel.of(notificacion,
            linkTo(methodOn(NotificacionControllerV2.class).obtenerPorId(notificacion.getId())).withSelfRel(),
            linkTo(methodOn(NotificacionController.class).obtenerPorDestinatario(notificacion.getDestinatario())).withRel("filtrar-por-destinatario"),
            linkTo(methodOn(NotificacionController.class).obtenerPorTipo(notificacion.getTipo())).withRel("filtrar-por-tipo")
        );
    }
}