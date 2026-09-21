package Parcial;

import java.util.*;

public class main {

    static ArrayList<vehiculo> vehiculos = new ArrayList<>();
    static HashSet<String> placas = new HashSet<>();
    static HashMap<String, Double> recaudacion = new HashMap<>();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        recaudacion.put("automovil", 0.0);
        recaudacion.put("motocicleta", 0.0);

        int opcion;

        do {
            mostrarMenu();

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {

                    case 1:
                        registrarVehiculo();
                        break;

                    case 2:
                        mostrarVehiculos();
                        break;

                    case 3:
                        buscarVehiculo();
                        break;

                    case 4:
                        mostrarMayorCosto();
                        break;

                    case 5:
                        mostrarTotalGeneral();
                        break;

                    case 6:
                        mostrarTotalesTipo();
                        break;

                    case 7:
                        System.out.println("Programa finalizado.");
                        break;

                    default:
                        System.out.println("Opcion invalida.");

                }

            } catch (NumberFormatException e) {

                System.out.println("Debe ingresar un numero valido.");
                opcion = 0;

            } finally {
                System.out.println("Operacion finalizada.");
            }

        } while (opcion != 7);
    }

    static void mostrarMenu() {

        System.out.println("\n===== ESTACIONAMIENTO =====");
        System.out.println("1. Registrar vehiculo");
        System.out.println("2. Mostrar vehiculos");
        System.out.println("3. Buscar por placa");
        System.out.println("4. Vehiculo de mayor costo");
        System.out.println("5. Total general recaudado");
        System.out.println("6. Total por tipo");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    static void registrarVehiculo() {

        System.out.println("\n--- REGISTRAR VEHICULO ---");

        System.out.print("Placa: ");
        String placa = scanner.nextLine().trim();

        if (placa.isEmpty()) {
            System.out.println("La placa no puede estar vacia.");
            return;
        }

        if (placas.contains(placa)) {
            System.out.println("La placa ya esta registrada.");
            return;
        }

        System.out.print("Propietario: ");
        String propietario = scanner.nextLine().trim();

        if (propietario.isEmpty()) {
            System.out.println("El propietario no puede estar vacio.");
            return;
        }

        System.out.print("Hora de ingreso: ");
        String horaIngreso = scanner.nextLine();

        int horas;

        try {

            System.out.print("Horas utilizadas: ");
            horas = Integer.parseInt(scanner.nextLine());

            if (horas <= 0) {
                System.out.println("Las horas deben ser mayores que cero.");
                return;
            }

        } catch (NumberFormatException e) {

            System.out.println("Las horas deben ser un numero.");
            return;
        }

        System.out.println("1. Automovil");
        System.out.println("2. Motocicleta");
        System.out.print("Tipo: ");

        int tipo;

        try {
            tipo = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Tipo invalido.");
            return;
        }

        vehiculo vehiculo;

        if (tipo == 1) {

            vehiculo = new automovil(
                    placa, propietario, horaIngreso, horas
            );

        } else if (tipo == 2) {

            vehiculo = new motocicleta(
                    placa, propietario, horaIngreso, horas
            );

        } else {

            System.out.println("Tipo de vehiculo invalido.");
            return;
        }

        vehiculos.add(vehiculo);
        placas.add(placa);

        String tipoVehiculo = vehiculo.getTipo();

        recaudacion.put(
                tipoVehiculo,
                recaudacion.get(tipoVehiculo) + vehiculo.calcularCosto()
        );

        System.out.println("Vehiculo registrado correctamente.");
    }

    static void mostrarVehiculos() {

        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehiculos registrados.");
            return;
        }

        System.out.println("\n--- VEHICULOS ---");

        for (vehiculo v : vehiculos) {
            v.mostrarInformacion();
            System.out.println("-------------------------");
        }
    }

    static void buscarVehiculo() {

        System.out.print("Ingrese la placa: ");
        String placa = scanner.nextLine();

        for (vehiculo v : vehiculos) {

            if (v.getPlaca().equalsIgnoreCase(placa)) {

                System.out.println("\nVehiculo encontrado:");
                v.mostrarInformacion();
                return;
            }
        }

        System.out.println("Vehiculo no encontrado.");
    }

    static void mostrarMayorCosto() {

        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehiculos registrados.");
            return;
        }

        vehiculo mayor = vehiculos.get(0);

        for (vehiculo v : vehiculos) {

            if (v.calcularCosto() > mayor.calcularCosto()) {
                mayor = v;
            }
        }

        System.out.println("\n--- MAYOR COSTO ---");
        mayor.mostrarInformacion();
    }

    static void mostrarTotalGeneral() {

        double total = 0;

        for (vehiculo v : vehiculos) {
            total += v.calcularCosto();
        }

        System.out.printf("Total general: Q%.2f%n", total);
    }

    static void mostrarTotalesTipo() {

        System.out.println("\n--- TOTAL POR TIPO ---");

        for (Map.Entry<String, Double> entrada : recaudacion.entrySet()) {

            System.out.printf(
                    "%s: Q%.2f%n",
                    entrada.getKey(),
                    entrada.getValue()
            );
        }
    }
}