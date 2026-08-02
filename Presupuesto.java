package semana3;

import java.util.Scanner;

public class Presupuesto    {

    // total gastado
    public static double totalGastado(double comida, double transporte, double otros) {
        return comida + transporte + otros;
    }

    // calculo de saldo
    public static double saldoDisponible(double presupuesto, double total) {
        return presupuesto - total;
    }

    // resumen
    public static void mostrarResultado(String nombre, double presupuesto, double total, double saldo) {

        System.out.println("\n----- RESUMEN SEMANAL -----");
        System.out.println("Nombre: " + nombre);
        System.out.println("Presupuesto: Q" + presupuesto);
        System.out.println("Total gastado: Q" + total);
        System.out.println("Saldo disponible: Q" + saldo);

        if (saldo > 0) {
            System.out.println("Estado: Dentro del presupuesto");
        } else if (saldo == 0) {
            System.out.println("Estado: Presupuesto agotado");
        } else {
            System.out.println("Estado: Excedió el presupuesto");
        }
    }

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        String nombre;
        double presupuesto;
        double comida;
        double transporte;
        double otros;
        double total;
        double saldo;

        System.out.print("Ingrese su nombre: ");
        nombre = entrada.nextLine();

        System.out.print("Ingrese el presupuesto semanal: ");
        presupuesto = entrada.nextDouble();

        System.out.print("Ingrese el gasto de alimentación: ");
        comida = entrada.nextDouble();

        System.out.print("Ingrese el gasto de transporte: ");
        transporte = entrada.nextDouble();

        System.out.print("Ingrese el gasto de otros: ");
        otros = entrada.nextDouble();

        total = totalGastado(comida, transporte, otros);
        saldo = saldoDisponible(presupuesto, total);

        mostrarResultado(nombre, presupuesto, total, saldo);

        entrada.close();
    }
}
