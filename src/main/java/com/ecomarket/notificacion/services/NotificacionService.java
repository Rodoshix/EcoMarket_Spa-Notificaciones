package com.ecomarket.notificacion.services;

import com.ecomarket.notificacion.dto.NotificacionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreo(NotificacionDTO dto) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(dto.getDestinatario());
            mensaje.setSubject(dto.getAsunto());
            mensaje.setText(dto.getCuerpo());
            mensaje.setFrom("notificaciones@ecomarket.cl"); // este campo será ignorado por Mailtrap pero debe estar

            mailSender.send(mensaje);
        } catch (Exception e) {
            throw new RuntimeException("Fallo al enviar el correo", e);
        }
    }
}