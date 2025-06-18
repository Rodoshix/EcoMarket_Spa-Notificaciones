package com.ecomarket.notificacion.services;

import com.ecomarket.notificacion.dto.NotificacionDTO;
import com.ecomarket.notificacion.model.Notificacion;
import com.ecomarket.notificacion.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private NotificacionRepository notificacionRepository;

    public void enviarCorreo(NotificacionDTO dto) {
        try {
            // 1. Guardar la notificación en la base de datos
            Notificacion notificacion = new Notificacion();
            notificacion.setDestinatario(dto.getDestinatario());
            notificacion.setMensaje(dto.getCuerpo());
            notificacion.setTipo("email"); // si más adelante tienes sms, podrías hacerlo dinámico
            notificacionRepository.save(notificacion);

            // 2. Enviar el correo real
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(dto.getDestinatario());
            mensaje.setSubject(dto.getAsunto());
            mensaje.setText(dto.getCuerpo());
            mensaje.setFrom("notificaciones@ecomarket.cl"); // Mailtrap lo ignora, pero es obligatorio

            mailSender.send(mensaje);
        } catch (Exception e) {
            throw new RuntimeException("Fallo al enviar el correo", e);
        }
    }
}