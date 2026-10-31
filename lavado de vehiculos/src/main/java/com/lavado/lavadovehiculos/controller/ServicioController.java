
package com.lavado.lavadovehiculos.controller;

import com.lavado.lavadovehiculos.dto.ServicioRequestDTO;
import com.lavado.lavadovehiculos.entity.Servicio;
import com.lavado.lavadovehiculos.service.ServicioService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    // Listar todos los servicios
    @GetMapping
    public List<Servicio> listarServicios() {
        return servicioService.listarServicios();
    }

    // Listar únicamente los servicios activos
    @GetMapping("/activos")
    public List<Servicio> listarActivos() {
        return servicioService.listarActivos();
    }

    // Consultar un servicio por su ID
    @GetMapping("/{id}")
    public Servicio obtenerServicio(@PathVariable Long id) {
        return servicioService.obtenerServicio(id);
    }

    // Crear un servicio
    @PostMapping
    public ResponseEntity<Servicio> crearServicio(
            @Valid @RequestBody ServicioRequestDTO datos) {

        Servicio nuevoServicio =
                servicioService.crearServicio(datos);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(nuevoServicio);
    }

    // Actualizar un servicio existente
    @PutMapping("/{id}")
    public Servicio actualizarServicio(
            @PathVariable Long id,
            @Valid @RequestBody ServicioRequestDTO datos) {

        return servicioService.actualizarServicio(id, datos);
    }

    // Eliminar un servicio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarServicio(
            @PathVariable Long id) {

        servicioService.eliminarServicio(id);

        return ResponseEntity.noContent().build();
    }
}
