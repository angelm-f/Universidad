
package com.lavado.lavadovehiculos.controller;

import com.lavado.lavadovehiculos.dto.OrdenRequestDTO;
import com.lavado.lavadovehiculos.entity.Orden;
import com.lavado.lavadovehiculos.service.OrdenService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    // Listar todas las órdenes
    @GetMapping
    public List<Orden> listarOrdenes() {
        return ordenService.listarOrdenes();
    }

    // Consultar una orden por su ID
    @GetMapping("/{id}")
    public Orden obtenerOrden(@PathVariable Long id) {
        return ordenService.obtenerOrden(id);
    }

    // Buscar órdenes por estado
    @GetMapping("/estado/{estado}")
    public List<Orden> buscarPorEstado(
            @PathVariable String estado) {
        return ordenService.buscarPorEstado(estado);
    }

    // Buscar órdenes por placa
    @GetMapping("/placa/{placa}")
    public List<Orden> buscarPorPlaca(
            @PathVariable String placa) {
        return ordenService.buscarPorPlaca(placa);
    }

    // Crear una nueva orden
    @PostMapping
public ResponseEntity<Orden> crearOrden(
        @Valid @RequestBody OrdenRequestDTO datos) {

    Orden nuevaOrden = ordenService.crearOrden(datos);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(nuevaOrden);
}

    // Actualizar una orden existente
    @PutMapping("/{id}")
public Orden actualizarOrden(
        @PathVariable Long id,
        @Valid @RequestBody OrdenRequestDTO datos) {

    return ordenService.actualizarOrden(id, datos);
}

    // Cambiar el estado de una orden
    @PatchMapping("/{id}/estado")
    public Orden cambiarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {

        return ordenService.cambiarEstado(id, estado);
    }

// Registrar el pago de una orden
@PatchMapping("/{id}/pago")
public Orden pagarOrden(@PathVariable Long id) {
    return ordenService.pagarOrden(id);
}

// Consultar los ingresos pagados del día
@GetMapping("/indicadores/ingresos-hoy")
public java.math.BigDecimal obtenerIngresosDelDia() {
    return ordenService.obtenerIngresosDelDia();
}

    // Eliminar una orden
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOrden(
            @PathVariable Long id) {

        ordenService.eliminarOrden(id);

        return ResponseEntity.noContent().build();
    }
}
