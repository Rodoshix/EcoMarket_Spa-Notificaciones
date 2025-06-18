package com.ecomarket.notificacion.controller;

import com.ecomarket.notificacion.model.Notificacion;
import com.ecomarket.notificacion.repository.NotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NotificacionControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NotificacionRepository repository;

    private Notificacion noti;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        noti = new Notificacion();
        noti.setDestinatario("cliente@correo.com");
        noti.setMensaje("Tu pedido fue enviado");
        noti.setTipo("email");
        repository.save(noti);
    }

    @Test
    void testObtenerTodasConHateoas() throws Exception {
        mockMvc.perform(get("/api/v2/notificaciones").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").exists())
                .andExpect(jsonPath("$._embedded.notificacionList").isArray())
                .andExpect(jsonPath("$._embedded.notificacionList[0]._links").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void testObtenerPorIdConHateoas() throws Exception {
        mockMvc.perform(get("/api/v2/notificaciones/{id}", noti.getId()).accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(noti.getId()))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.filtrar-por-destinatario.href").exists())
                .andExpect(jsonPath("$._links.filtrar-por-tipo.href").exists());
    }

    @Test
    void testObtenerPorDestinatarioConHateoas() throws Exception {
        mockMvc.perform(get("/api/v2/notificaciones/destinatario/{correo}", "cliente@correo.com").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.notificacionList").isArray())
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void testObtenerPorTipoConHateoas() throws Exception {
        mockMvc.perform(get("/api/v2/notificaciones/tipo/{tipo}", "email").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.notificacionList").isArray())
                .andExpect(jsonPath("$._links.self.href").exists());
    }
}