package com.ecomarket.notificacion.performance;

import com.ecomarket.notificacion.dto.NotificacionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class NotificacionPerformanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void testTiempoEnvioCorreo() {
        String url = "http://localhost:" + port + "/api/notificaciones/enviar";

        NotificacionDTO dto = new NotificacionDTO();
        dto.setDestinatario("cliente@correo.com");
        dto.setAsunto("Test Rendimiento");
        dto.setCuerpo("Mensaje de prueba");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(
                objectMapper.valueToTree(dto).toString(), headers);

        StopWatch watch = new StopWatch();
        watch.start();

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        watch.stop();
        long tiempo = watch.getTotalTimeMillis();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("⏱️ Tiempo de respuesta: " + tiempo + " ms");
        assertTrue(tiempo < 1000, "El tiempo de respuesta superó 1 segundo");
    }
}