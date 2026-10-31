
package com.lavado.lavadovehiculos.service;

import com.lavado.lavadovehiculos.dto.OrdenRequestDTO;
import com.lavado.lavadovehiculos.entity.Orden;
import com.lavado.lavadovehiculos.entity.Servicio;
import com.lavado.lavadovehiculos.repository.OrdenRepository;
import com.lavado.lavadovehiculos.repository.ServicioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.math.RoundingMode;

@Service
@Transactional
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final ServicioRepository servicioRepository;

    public OrdenService(
            OrdenRepository ordenRepository,
            ServicioRepository servicioRepository) {
        this.ordenRepository = ordenRepository;
        this.servicioRepository = servicioRepository;
    }

    @Transactional(readOnly = true)
    public List<Orden> listarOrdenes() {
        return ordenRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Orden obtenerOrden(Long id) {
        return ordenRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Orden no encontrada"));
    }

    @Transactional(readOnly = true)
    public List<Orden> buscarPorEstado(String estado) {
        if (estado == null || estado.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El estado es obligatorio");
        }

        return ordenRepository.findByEstadoIgnoreCase(
                estado.trim().toUpperCase(Locale.ROOT));
    }

    @Transactional(readOnly = true)
    public List<Orden> buscarPorPlaca(String placa) {
        if (placa == null || placa.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "La placa es obligatoria");
        }

        return ordenRepository.findByPlacaContainingIgnoreCase(placa.trim());
    }

    // Crear una orden a partir del DTO
    public Orden crearOrden(OrdenRequestDTO datos) {
        validarDatosObligatorios(datos);

        Servicio servicio = obtenerServicioActivo(datos.getServicioId());

        String tipo = normalizarTipoVehiculo(datos.getTipoVehiculo());

        BigDecimal recargo = datos.getRecargo() == null
                ? BigDecimal.ZERO
                : datos.getRecargo();

        validarRecargo(recargo);

        // Convertir los datos del DTO a una entidad persistible.
        Orden orden = new Orden();
        orden.setPlaca(datos.getPlaca().trim().toUpperCase(Locale.ROOT));
        orden.setPropietario(datos.getPropietario().trim());
        orden.setTipoVehiculo(tipo);
        orden.setServicio(servicio);
        orden.setRecargo(recargo);
        orden.setTotal(calcularTotal(servicio, recargo));
        orden.setEstado("RECIBIDA");
        orden.setFecha(null);
        orden.setFechaPago(null);

        // Se guarda la entidad, nunca el DTO.
        return ordenRepository.save(orden);
    }

    // Actualizar una orden existente a partir del DTO
    public Orden actualizarOrden(Long id, OrdenRequestDTO datos) {
        validarDatosObligatorios(datos);

        Orden existente = obtenerOrden(id);
        Servicio servicio = obtenerServicioActivo(datos.getServicioId());

        String tipo = normalizarTipoVehiculo(datos.getTipoVehiculo());

        BigDecimal recargo = datos.getRecargo() == null
                ? BigDecimal.ZERO
                : datos.getRecargo();

        validarRecargo(recargo);

        existente.setPlaca(datos.getPlaca().trim().toUpperCase(Locale.ROOT));
        existente.setPropietario(datos.getPropietario().trim());
        existente.setTipoVehiculo(tipo);
        existente.setServicio(servicio);
        existente.setRecargo(recargo);
        existente.setTotal(calcularTotal(servicio, recargo));

        // Se conserva el estado y la fecha de pago existentes.
        return ordenRepository.save(existente);
    }

    // Permitir únicamente el avance secuencial de los estados
    public Orden cambiarEstado(Long id, String estado) {
        Orden orden = obtenerOrden(id);

        if (estado == null || estado.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El nuevo estado es obligatorio");
        }

        String actual = orden.getEstado() == null
                ? ""
                : orden.getEstado().trim().toUpperCase(Locale.ROOT);

        String nuevo = estado.trim().toUpperCase(Locale.ROOT);

        boolean permitida =
                ("RECIBIDA".equals(actual) && "EN_PROCESO".equals(nuevo))
                || ("EN_PROCESO".equals(actual) && "LISTA".equals(nuevo))
                || ("LISTA".equals(actual) && "ENTREGADA".equals(nuevo));

        if (!permitida) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Transición de estado no permitida: "
                            + actual + " -> " + nuevo);
        }

        orden.setEstado(nuevo);
        return ordenRepository.save(orden);
    }

// Registrar el pago de una orden
public Orden pagarOrden(Long id) {

    Orden orden = obtenerOrden(id);

    // Impedir que una orden se cobre dos veces
    if (orden.getFechaPago() != null) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Esta orden ya fue pagada"
        );
    }

    // Solo permitir el pago cuando el servicio esté listo
    // o el vehículo ya haya sido entregado.
    String estado = orden.getEstado() == null
            ? ""
            : orden.getEstado().trim().toUpperCase(Locale.ROOT);

    if (!"LISTA".equals(estado)
            && !"ENTREGADA".equals(estado)) {

        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "La orden debe estar LISTA o ENTREGADA "
                        + "para registrar el pago"
        );
    }

    orden.setFechaPago(LocalDateTime.now());

    return ordenRepository.save(orden);
}

// Calcular los ingresos correspondientes al día actual
@Transactional(readOnly = true)
public BigDecimal obtenerIngresosDelDia() {

    LocalDate hoy = LocalDate.now();

    LocalDateTime inicio = hoy.atStartOfDay();
    LocalDateTime fin = hoy.plusDays(1).atStartOfDay();

    BigDecimal ingresos = ordenRepository.sumarIngresosPagados(
            inicio,
            fin
    );

    if (ingresos == null) {
        ingresos = BigDecimal.ZERO;
    }

    return ingresos.setScale(2, RoundingMode.HALF_UP);
}

    public void eliminarOrden(Long id) {
    Orden orden = obtenerOrden(id);

    // No permitir eliminar órdenes que ya fueron pagadas.
    if (orden.getFechaPago() != null) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "No se puede eliminar una orden que ya fue pagada"
        );
    }

    ordenRepository.delete(orden);
}

    // Validar los campos recibidos en el DTO
    private void validarDatosObligatorios(OrdenRequestDTO datos) {
        if (datos == null
                || datos.getPlaca() == null
                || datos.getPlaca().isBlank()
                || datos.getPropietario() == null
                || datos.getPropietario().isBlank()
                || datos.getTipoVehiculo() == null
                || datos.getTipoVehiculo().isBlank()
                || datos.getServicioId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La placa, el propietario, el tipo de vehículo "
                            + "y el servicio son obligatorios");
        }
    }

    private Servicio obtenerServicioActivo(Long id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "El servicio seleccionado no existe"));

        if (!Boolean.TRUE.equals(servicio.getActivo())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El servicio seleccionado está inactivo");
        }

        if (servicio.getPrecio() == null
                || servicio.getPrecio().compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El servicio tiene un precio inválido");
        }

        return servicio;
    }

    private String normalizarTipoVehiculo(String tipo) {
        String normalizado = tipo.trim().toUpperCase(Locale.ROOT);

        if (!List.of("AUTOMOVIL", "MOTOCICLETA", "CAMIONETA", "OTRO")
                .contains(normalizado)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Tipo de vehículo no válido");
        }

        return normalizado;
    }

    private void validarRecargo(BigDecimal recargo) {
        if (recargo == null
                || recargo.compareTo(BigDecimal.ZERO) < 0
                || recargo.scale() > 2) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El recargo debe ser mayor o igual a cero "
                            + "y no puede tener más de dos decimales");
        }
    }

    private BigDecimal calcularTotal(Servicio servicio, BigDecimal recargo) {
        return servicio.getPrecio().add(recargo);
    }
}
