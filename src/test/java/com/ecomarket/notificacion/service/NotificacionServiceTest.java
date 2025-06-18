package com.ecomarket.notificacion.service;

import com.ecomarket.notificacion.dto.NotificacionDTO;
import com.ecomarket.notificacion.model.Notificacion;
import com.ecomarket.notificacion.repository.NotificacionRepository;
import com.ecomarket.notificacion.services.NotificacionService;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class NotificacionServiceTest {

    @Autowired
    private NotificacionService notificacionService;

    @MockBean
    private NotificacionRepository notificacionRepository;

    @MockBean
    private JavaMailSender mailSender;

    @Test
    void testEnviarCorreo_guardaYEnviaCorreo() {
        // Arrange
        NotificacionDTO dto = new NotificacionDTO();
        dto.setDestinatario("cliente@correo.com");
        dto.setAsunto("Test Unitario");
        dto.setCuerpo("Contenido de prueba");

        // Act
        notificacionService.enviarCorreo(dto);

        // Assert

        // 1. Verifica que se haya guardado la notificación
        verify(notificacionRepository, times(1)).save(any(Notificacion.class));

        // 2. Verifica que se haya enviado el correo
        ArgumentCaptor<SimpleMailMessage> mailCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(mailCaptor.capture());

        SimpleMailMessage mensajeEnviado = mailCaptor.getValue();
        assertEquals("cliente@correo.com", mensajeEnviado.getTo()[0]);
        assertEquals("Test Unitario", mensajeEnviado.getSubject());
        assertEquals("Contenido de prueba", mensajeEnviado.getText());
    }
}