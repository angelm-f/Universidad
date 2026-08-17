package Parqueo;

import java.util.Scanner;

public class parqueo {

    // Obtiene la tarifa dependiendo del tipo de vehículo
    public static double obtenerTarifa(int tipoVehiculo) {
        switch (tipoVehiculo) {
            case 1:
                return 5.00;
            case 2:
                return 8.00;
            case 3:
                return 12.00;
            default:
                return 0.00;
        }
    }

    // Obtiene el nombre del tipo de vehículo
    public static String obtenerNombreVehiculo(int tipoVehiculo) {
        switch (tipoVehiculo) {
            case 1:
                return "Motocicleta";
            case 2:
                return "Automóvil";
            case 3:
                return "Pickup o camioneta";
            default:
                return "Desconocido";
        }
    }

    // Calcula el descuento del 15% si permanece más de 8 horas
    public static double calcularDescuento(double subtotal, int horas) {
        if (horas > 8) {
            return subtotal * 0.15;
        } else {
            return 0.00;
        }
    }

    // Calcula el pago cuando no se perdió el ticket
    public static double calcularPago(int horas, double tarifa) {
        return horas * tarifa;
    }

    // Sobrecarga de calcularPago cuando existe un recargo
    public static double calcularPago(int horas, double tarifa, double recargo) {
        return (horas * tarifa) + recargo;
    }

    // Muestra el comprobante de cada vehículo
    public static void mostrarComprobante(
            String placa,
            String nombreVehiculo,
            int horas,
            double tarifa,
            double subtotal,
            double descuento,
            double recargo,
            double total) {

        System.out.println();
        System.out.println("========== COMPROBANTE ==========");
        System.out.println("Placa: " + placa);
        System.out.println("Tipo: " + nombreVehiculo);
        System.out.println("Horas estacionado: " + horas);
        System.out.printf("Tarifa por hora: Q%.2f%n", tarifa);
        System.out.printf("Subtotal: Q%.2f%n", subtotal);
        System.out.printf("Descuento: Q%.2f%n", descuento);
        System.out.printf("Recargo por ticket perdido: Q%.2f%n", recargo);
        System.out.printf("TOTAL: Q%.2f%n", total);
        System.out.println("=================================");
    }

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        int cantidadVehiculos;

        // Validar cantidad de vehículos
        do {
            System.out.print("Ingrese la cantidad de vehículos: ");
            cantidadVehiculos = entrada.nextInt();

            if (cantidadVehiculos <= 0) {
                System.out.println("Error: la cantidad debe ser mayor que cero.");
            }

        } while (cantidadVehiculos <= 0);

        // Contadores
        int cantidadMotocicletas = 0;
        int cantidadAutomoviles = 0;
        int cantidadPickups = 0;
        int cantidadTicketsPerdidos = 0;

        // Acumulador
        double totalRecaudado = 0.00;

        // Datos del vehículo con mayor pago
        double pagoMasAlto = 0.00;
        String placaPagoMasAlto = "";

        // Procesar cada vehículo
        for (int i = 1; i <= cantidadVehiculos; i++) {

            System.out.println();
            System.out.println("========== VEHÍCULO " + i + " ==========");

            entrada.nextLine();

            System.out.print("Ingrese el número de placa: ");
            String placa = entrada.nextLine();

            // Validar tipo de vehículo
            int tipoVehiculo;

            do {
                System.out.println("1. Motocicleta");
                System.out.println("2. Automóvil");
                System.out.println("3. Pickup o camioneta");
                System.out.print("Seleccione el tipo de vehículo: ");

                tipoVehiculo = entrada.nextInt();

                if (tipoVehiculo < 1 || tipoVehiculo > 3) {
                    System.out.println("Error: debe seleccionar 1, 2 o 3.");
                }

            } while (tipoVehiculo < 1 || tipoVehiculo > 3);

            // Validar horas
            int horas;

            do {
                System.out.print("Ingrese las horas estacionado: ");
                horas = entrada.nextInt();

                if (horas <= 0) {
                    System.out.println("Error: las horas deben ser mayores que cero.");
                }

            } while (horas <= 0);

            // Validar ticket
            String ticket;

            do {
                System.out.print("¿Perdió el ticket? (S/N): ");
                ticket = entrada.next().toUpperCase();

                if (!ticket.equals("S") && !ticket.equals("N")) {
                    System.out.println("Error: solamente debe ingresar S o N.");
                }

            } while (!ticket.equals("S") && !ticket.equals("N"));

            // Obtener tarifa y nombre
            double tarifa = obtenerTarifa(tipoVehiculo);
            String nombreVehiculo = obtenerNombreVehiculo(tipoVehiculo);

            // Calcular subtotal
            double subtotal = horas * tarifa;

            // Calcular descuento
            double descuento = calcularDescuento(subtotal, horas);

            // Calcular recargo
            double recargo = 0.00;

            if (ticket.equals("S")) {
                recargo = 50.00;
                cantidadTicketsPerdidos++;
            }

            // Calcular total
            double total;

            if (ticket.equals("S")) {
                // Se utiliza la sobrecarga con recargo
                total = calcularPago(horas, tarifa, recargo) - descuento;
            } else {
                // Se utiliza la sobrecarga sin recargo
                total = calcularPago(horas, tarifa) - descuento;
            }

            // Contadores según el tipo de vehículo
            if (tipoVehiculo == 1) {
                cantidadMotocicletas++;
            } else if (tipoVehiculo == 2) {
                cantidadAutomoviles++;
            } else {
                cantidadPickups++;
            }

            // Acumular dinero recaudado
            totalRecaudado = totalRecaudado + total;

            // Determinar el pago más alto
            if (total > pagoMasAlto) {
                pagoMasAlto = total;
                placaPagoMasAlto = placa;
            }

            // Mostrar comprobante
            mostrarComprobante(
                    placa,
                    nombreVehiculo,
                    horas,
                    tarifa,
                    subtotal,
                    descuento,
                    recargo,
                    total
            );
        }

        // Resumen final
        System.out.println();
        System.out.println("======================================");
        System.out.println("       RESUMEN DE LA JORNADA");
        System.out.println("======================================");
        System.out.println("Cantidad de motocicletas: " + cantidadMotocicletas);
        System.out.println("Cantidad de automóviles: " + cantidadAutomoviles);
        System.out.println("Cantidad de pickups/camionetas: " + cantidadPickups);
        System.out.println("Cantidad de tickets perdidos: " + cantidadTicketsPerdidos);
        System.out.printf("Total de dinero recaudado: Q%.2f%n", totalRecaudado);
        System.out.printf("Pago más alto: Q%.2f%n", pagoMasAlto);
        System.out.println("Placa del pago más alto: " + placaPagoMasAlto);
        System.out.println("======================================");

        entrada.close();
    }
}