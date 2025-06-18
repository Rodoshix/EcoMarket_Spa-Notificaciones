package com.ecomarket.notificacion.controller;

import com.ecomarket.notificacion.dto.NotificacionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testEnviarNotificacion() throws Exception {
        NotificacionDTO dto = new NotificacionDTO();
        dto.setDestinatario("cliente@correo.com");
        dto.setAsunto("Bienvenido");
        dto.setCuerpo("Gracias por registrarte");

        mockMvc.perform(post("/api/notificaciones/enviar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Correo enviado correctamente"));
    }

    @Test
    void testObtenerTodas() throws Exception {
        mockMvc.perform(get("/api/notificaciones"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testObtenerPorDestinatario() throws Exception {
        String correo = "cliente@correo.com";

        mockMvc.perform(get("/api/notificaciones/destinatario/{correo}", correo))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testObtenerPorTipo() throws Exception {
        String tipo = "email";

        mockMvc.perform(get("/api/notificaciones/tipo/{tipo}", tipo))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}