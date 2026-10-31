package com.lavado.lavadovehiculos.repository;

import com.lavado.lavadovehiculos.entity.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Long> {

    List<Orden> findByEstadoIgnoreCase(String estado);

    List<Orden> findByPlacaContainingIgnoreCase(String placa);

    List<Orden> findByPropietarioContainingIgnoreCase(String propietario);

    // Buscar órdenes pagadas dentro de un intervalo de fechas
    List<Orden> findByFechaPagoGreaterThanEqualAndFechaPagoLessThan(
            LocalDateTime inicio,
            LocalDateTime fin
    );

    // Sumar únicamente los totales de las órdenes pagadas en el intervalo
    @Query("""
        SELECT COALESCE(SUM(o.total), 0)
        FROM Orden o
        WHERE o.fechaPago >= :inicio
          AND o.fechaPago < :fin
    """)
    BigDecimal sumarIngresosPagados(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );
}