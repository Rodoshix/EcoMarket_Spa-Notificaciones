package com.ecomarket.notificacion.controller;

import com.ecomarket.notificacion.dto.NotificacionDTO;
import com.ecomarket.notificacion.services.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @PostMapping("/enviar")
    public ResponseEntity<String> enviar(@RequestBody NotificacionDTO dto) {
        notificacionService.enviarCorreo(dto);
        return ResponseEntity.ok("Correo enviado correctamente");
    }
}