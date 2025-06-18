package com.ecomarket.notificacion.controller;

import com.ecomarket.notificacion.dto.NotificacionDTO;
import com.ecomarket.notificacion.model.Notificacion;
import com.ecomarket.notificacion.repository.NotificacionRepository;
import com.ecomarket.notificacion.services.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private NotificacionRepository notificacionRepository;

    // Enviar correo y guardar en BD
    @PostMapping("/enviar")
    public ResponseEntity<String> enviar(@RequestBody NotificacionDTO dto) {
        notificacionService.enviarCorreo(dto);
        return ResponseEntity.ok("Correo enviado correctamente");
    }

    // Consultar todas las notificaciones guardadas
    @GetMapping
    public ResponseEntity<List<Notificacion>> obtenerTodas() {
        List<Notificacion> notificaciones = notificacionRepository.findAll();
        return ResponseEntity.ok(notificaciones);
    }

    // Filtrar por destinatario (correo)
    @GetMapping("/destinatario/{correo}")
    public ResponseEntity<List<Notificacion>> obtenerPorDestinatario(@PathVariable String correo) {
        List<Notificacion> resultado = notificacionRepository.findByDestinatario(correo);
        return ResponseEntity.ok(resultado);
    }

    // Filtrar por tipo (ej: email)
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Notificacion>> obtenerPorTipo(@PathVariable String tipo) {
        List<Notificacion> resultado = notificacionRepository.findByTipo(tipo);
        return ResponseEntity.ok(resultado);
    }
}