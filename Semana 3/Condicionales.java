package semana3;

import java.util.Scanner;

public class Condicionales  {
    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        int opcion;

        System.out.println("Estudiante: Angel Martinez");
        System.out.println("Carnét: 9941-25-1530");
        System.out.println("Semana 3 - Condiciones y ciclos");

        do {

            System.out.println("\nDESAFÍOS LÓGICOS");
            System.out.println("1. Generar una secuencia");
            System.out.println("2. Realizar un conteo regresivo");
            System.out.println("3. Analizar números");
            System.out.println("4. Dibujar una pirámide");
            System.out.println("5. Validar palabra secreta");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = entrada.nextInt();

            switch (opcion) {

                // OPCION 1
                case 1:

                    System.out.print("Número inicial: ");
                    int inicio = entrada.nextInt();

                    System.out.print("Número final: ");
                    int fin = entrada.nextInt();

                    System.out.print("Incremento: ");
                    int incremento = entrada.nextInt();

                    if (incremento <= 0) {
                        System.out.println("El incremento debe ser mayor que cero.");
                    } else if (fin <= inicio) {
                        System.out.println("El número final debe ser mayor que el inicial.");
                    } else {

                        System.out.println("Secuencia:");

                        for (int i = inicio; i <= fin; i += incremento) {
                            System.out.print(i + " ");
                        }

                        System.out.println();
                    }

                    break;

                // OPCION 2
                case 2:

                    int numero;

                    System.out.print("Ingrese un número entre 10 y 50: ");
                    numero = entrada.nextInt();

                    while (numero < 10 || numero > 50) {
                        System.out.println("Número inválido.");
                        System.out.print("Ingrese nuevamente: ");
                        numero = entrada.nextInt();
                    }

                    while (numero >= 0) {
                        System.out.print(numero + " ");
                        numero--;
                    }

                    System.out.println("\n¡Despegue!");

                    break;

                // OPCION 3
                case 3:

                    int positivos = 0;
                    int negativos = 0;
                    int suma = 0;
                    int ignorados = 0;

                    while (true) {

                        System.out.print("Ingrese un número: ");
                        int n = entrada.nextInt();

                        if (n == 0) {
                            break;
                        }

                        if (n > 0) {
                            positivos++;
                        } else {
                            negativos++;
                        }

                        if (n % 5 == 0) {
                            System.out.println("El número " + n + " fue ignorado.");
                            ignorados++;
                            continue;
                        }

                        suma += n;
                    }

                    System.out.println("\nResultado");
                    System.out.println("Positivos: " + positivos);
                    System.out.println("Negativos: " + negativos);
                    System.out.println("Suma válida: " + suma);
                    System.out.println("Números ignorados: " + ignorados);

                    break;

                // OPCION 4
                case 4:

                    System.out.print("Ingrese la altura (3-10): ");
                    int altura = entrada.nextInt();

                    if (altura < 3 || altura > 10) {
                        System.out.println("Altura inválida.");
                    } else {

                        for (int i = 1; i <= altura; i++) {

                            for (int j = 1; j <= altura - i; j++) {
                                System.out.print(" ");
                            }

                            for (int k = 1; k <= (2 * i) - 1; k++) {
                                System.out.print("*");
                            }

                            System.out.println();
                        }
                    }

                    break;

                // OPCION 5
                case 5:

                    entrada.nextLine();

                    String palabra;

                    do {

                        System.out.print("Ingrese la palabra secreta: ");
                        palabra = entrada.nextLine();

                        if (!palabra.trim().equalsIgnoreCase("Guatemala")) {
                            System.out.println("Palabra incorrecta. Intente nuevamente.");
                        }

                    } while (!palabra.trim().equalsIgnoreCase("Guatemala"));

                    System.out.println("Palabra correcta.");

                    break;

                // OPCION 6
                case 6:

                    System.out.println("Programa finalizado correctamente.");

                    break;

                default:

                    System.out.println("Opción no válida.");

            }

        } while (opcion != 6);

        entrada.close();

    }
}
