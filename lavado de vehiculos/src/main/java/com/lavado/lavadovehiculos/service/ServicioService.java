package com.lavado.lavadovehiculos.service;

import com.lavado.lavadovehiculos.dto.ServicioRequestDTO;
import com.lavado.lavadovehiculos.entity.Servicio;
import com.lavado.lavadovehiculos.repository.ServicioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    @Transactional(readOnly = true)
    public List<Servicio> listarServicios() {
        return servicioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Servicio> listarActivos() {
        return servicioRepository.findByActivoTrue();
    }

    @Transactional(readOnly = true)
    public Servicio obtenerServicio(Long id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Servicio no encontrado"));
    }

    public Servicio crearServicio(ServicioRequestDTO datos) {
        if (datos == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Los datos del servicio son obligatorios");
        }

        validarServicio(datos);

        String nombre = datos.getNombre().trim();
        comprobarNombreDisponible(nombre, null);

        Servicio servicio = new Servicio();
        servicio.setNombre(nombre);
        servicio.setDescripcion(
                normalizarDescripcion(datos.getDescripcion()));
        servicio.setPrecio(datos.getPrecio());
        servicio.setActivo(
                datos.getActivo() == null ? true : datos.getActivo());

        return servicioRepository.save(servicio);
    }

    public Servicio actualizarServicio(Long id, ServicioRequestDTO datos) {
        if (datos == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Los datos del servicio son obligatorios");
        }

        Servicio existente = obtenerServicio(id);
        validarServicio(datos);

        String nombre = datos.getNombre().trim();
        comprobarNombreDisponible(nombre, id);

        if (datos.getActivo() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe indicar si el servicio está activo");
        }

        existente.setNombre(nombre);
        existente.setDescripcion(
                normalizarDescripcion(datos.getDescripcion()));
        existente.setPrecio(datos.getPrecio());
        existente.setActivo(datos.getActivo());

        return servicioRepository.save(existente);
    }

    public void eliminarServicio(Long id) {
        Servicio existente = obtenerServicio(id);
        servicioRepository.delete(existente);
    }

    private void validarServicio(ServicioRequestDTO datos) {
        if (datos.getNombre() == null
                || datos.getNombre().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El nombre del servicio es obligatorio");
        }

        if (datos.getNombre().trim().length() > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El nombre del servicio no puede superar 100 caracteres");
        }

        if (datos.getDescripcion() != null
                && datos.getDescripcion().length() > 255) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La descripción no puede superar 255 caracteres");
        }

        BigDecimal precio = datos.getPrecio();

        if (precio == null
                || precio.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El precio debe ser mayor que cero");
        }

        if (precio.scale() > 2) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El precio no puede tener más de dos decimales");
        }
    }

    private String normalizarDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            return null;
        }

        return descripcion.trim();
    }

    private void comprobarNombreDisponible(
            String nombre, Long idActual) {

        List<Servicio> servicios = servicioRepository.findAll();

        boolean duplicado = servicios.stream()
                .filter(s -> s.getNombre() != null
                        && s.getNombre().equalsIgnoreCase(nombre))
                .anyMatch(s -> idActual == null
                        || !s.getId().equals(idActual));

        if (duplicado) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un servicio con ese nombre");
        }
    }
}
