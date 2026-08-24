package tarea4;

import java.util.Scanner;

public class Main {

    static Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {

        int opcion;

        do {
            System.out.println();
            System.out.println("================================");
            System.out.println("       SISTEMA DE ENVÍOS");
            System.out.println("================================");
            System.out.println("1. Registrar envío nacional");
            System.out.println("2. Registrar envío internacional");
            System.out.println("3. Salir");

            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {

                case 1:
                    registrarNacional();
                    break;

                case 2:
                    registrarInternacional();
                    break;

                case 3:
                    System.out.println("Programa finalizado.");
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }

        } while (opcion != 3);

        entrada.close();
    }

    public static void registrarNacional() {

        System.out.println();
        System.out.println("===== REGISTRO NACIONAL =====");

        String codigo = leerTexto("Código del envío: ");
        String destinatario = leerTexto("Nombre del destinatario: ");
        double peso = leerDoublePositivo("Peso en kilogramos: ");
        String departamento = leerTexto("Departamento de destino: ");
        double distancia = leerDoublePositivo("Distancia en kilómetros: ");

        Envio envio;

        envio = new EnvioNacional(
                codigo,
                destinatario,
                peso,
                departamento,
                distancia
        );

        System.out.println();
        envio.mostrarResumen(true);

        preguntarOtroEnvio();
    }

    public static void registrarInternacional() {

        System.out.println();
        System.out.println("===== REGISTRO INTERNACIONAL =====");

        String codigo = leerTexto("Código del envío: ");
        String destinatario = leerTexto("Nombre del destinatario: ");
        double peso = leerDoublePositivo("Peso en kilogramos: ");
        String pais = leerTexto("País de destino: ");

        Envio envio;

        envio = new EnvioInternacional(
                codigo,
                destinatario,
                peso,
                pais
        );

        System.out.println();
        envio.mostrarResumen(true);

        preguntarOtroEnvio();
    }

    public static void preguntarOtroEnvio() {

        System.out.println();
        System.out.println("¿Desea registrar otro envío?");
        System.out.println("1. Sí");
        System.out.println("2. No");

        int opcion = leerEntero("Seleccione una opción: ");

        if (opcion == 1) {
            return;
        }

        if (opcion == 2) {
            System.out.println("Regresando al menú principal...");
            return;
        }

        System.out.println("Opción inválida.");
    }

    public static String leerTexto(String mensaje) {

        String texto;

        do {
            System.out.print(mensaje);
            texto = entrada.nextLine().trim();

            if (texto.isEmpty()) {
                System.out.println("El dato no puede estar vacío.");
            }

        } while (texto.isEmpty());

        return texto;
    }

    public static double leerDoublePositivo(String mensaje) {

        double numero;

        while (true) {

            try {

                System.out.print(mensaje);
                numero = Double.parseDouble(entrada.nextLine());

                if (numero > 0) {
                    return numero;
                }

                System.out.println("El valor debe ser mayor que cero.");

            } catch (NumberFormatException e) {

                System.out.println("Ingrese un número válido.");
            }
        }
    }

    public static int leerEntero(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);
                return Integer.parseInt(entrada.nextLine());

            } catch (NumberFormatException e) {

                System.out.println("Ingrese una opción numérica válida.");
            }
        }
    }
}
