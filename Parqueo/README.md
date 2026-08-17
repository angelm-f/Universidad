# Tarea de parqueo

## Datos del estudiante

**Nombre:** Angel Martinez  
**Carné:** 9941 25 1530

## Descripción de la solución

El programa permite calcular el pago de varios vehículos que utilizan un parqueo.

Primero solicita la cantidad de vehículos que se van a registrar. Para cada vehículo se solicita el número de placa, el tipo de vehículo, las horas que permaneció estacionado y si el conductor perdió el ticket.

El programa utiliza diferentes tarifas dependiendo del tipo de vehículo:

- Motocicleta: Q5.00 por hora.
- Automóvil: Q8.00 por hora.
- Pickup o camioneta: Q12.00 por hora.

Si el vehículo permanece más de 8 horas, se aplica un descuento del 15 % sobre el pago del tiempo estacionado.

Si el conductor perdió el ticket, se agrega un recargo de Q50.00 después de calcular el descuento.

Al finalizar, el programa muestra un resumen de la jornada con la cantidad de vehículos de cada tipo, tickets perdidos, dinero recaudado y el vehículo que realizó el pago más alto.

## Métodos creados

### obtenerTarifa()

Recibe el tipo de vehículo y devuelve la tarifa correspondiente por hora.

### obtenerNombreVehiculo()

Recibe el tipo de vehículo y devuelve su nombre.

### calcularDescuento()

Calcula el descuento del 15 % cuando el vehículo permanece más de 8 horas.

### calcularPago()

Calcula el pago del vehículo.

Este método tiene dos versiones:

```text
calcularPago(int horas, double tarifa)