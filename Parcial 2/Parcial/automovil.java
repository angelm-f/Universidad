package Parcial;

public class automovil extends vehiculo {

    public automovil(String placa, String propietario, String horaIngreso, int horasUtilizadas) {
        super(placa, propietario, horaIngreso, horasUtilizadas);
    }

    @Override
    public double calcularCosto() {

        double costo = getHorasUtilizadas() * 10.00;

        if (getHorasUtilizadas() > 5) {
            costo = costo * 0.90;
        }

        return costo;
    }
}