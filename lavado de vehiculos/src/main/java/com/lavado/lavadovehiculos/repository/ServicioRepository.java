package com.lavado.lavadovehiculos.repository;

import com.lavado.lavadovehiculos.entity.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    // Consultar servicios activos
    List<Servicio> findByActivoTrue();

    // Comprobar servicio existente
    boolean existsByNombreIgnoreCase(String nombre);

}