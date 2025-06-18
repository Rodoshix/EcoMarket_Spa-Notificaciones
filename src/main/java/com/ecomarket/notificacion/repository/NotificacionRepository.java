package com.ecomarket.notificacion.repository;

import com.ecomarket.notificacion.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByDestinatario(String destinatario);

    List<Notificacion> findByTipo(String tipo);
}