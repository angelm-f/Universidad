
package com.lavado.lavadovehiculos.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class OrdenRequestDTO {

    @NotBlank(message = "La placa es obligatoria")
    @Size(max = 15, message = "La placa no puede superar los 15 caracteres")
    private String placa;

    @NotBlank(message = "El propietario es obligatorio")
    @Size(max = 120, message = "El nombre del propietario no puede superar los 120 caracteres")
    private String propietario;

    @NotBlank(message = "El tipo de vehículo es obligatorio")
    @Size(max = 30, message = "El tipo de vehículo no puede superar los 30 caracteres")
    private String tipoVehiculo;

    @NotNull(message = "Debe seleccionar un servicio")
    @Positive(message = "El identificador del servicio debe ser positivo")
    private Long servicioId;

    @DecimalMin(value = "0.00", message = "El recargo no puede ser negativo")
    @Digits(integer = 8, fraction = 2, message = "El recargo debe tener como máximo dos decimales")
    private BigDecimal recargo;

    public OrdenRequestDTO() {
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public Long getServicioId() {
        return servicioId;
    }

    public void setServicioId(Long servicioId) {
        this.servicioId = servicioId;
    }

    public BigDecimal getRecargo() {
        return recargo;
    }

    public void setRecargo(BigDecimal recargo) {
        this.recargo = recargo;
    }
}
