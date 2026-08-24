package tarea4;

public class EnvioNacional extends Envio {

    private String departamento;
    private double distancia;

    public EnvioNacional(String codigo, String destinatario, double peso,
                         String departamento, double distancia) {

        super(codigo, destinatario, peso);

        this.departamento = departamento;
        this.distancia = distancia;
    }

    public String getDepartamento() {
        return departamento;
    }

    public double getDistancia() {
        return distancia;
    }

    @Override
    public double calcularCostoFinal() {
        return calcularCostoBase() + (distancia * 0.50);
    }

    @Override
    protected void mostrarDesglose() {
        double costoBase = calcularCostoBase();
        double cargoDistancia = distancia * 0.50;
        double costoFinal = calcularCostoFinal();

        System.out.println("===== DESGLOSE ENVÍO NACIONAL =====");
        System.out.println("Código: " + getCodigo());
        System.out.println("Destinatario: " + getDestinatario());
        System.out.println("Departamento: " + departamento);
        System.out.println("Peso: " + getPeso() + " kg");
        System.out.println("Distancia: " + distancia + " km");

        System.out.printf("Costo base: Q%.2f%n", costoBase);
        System.out.printf("Cargo por distancia: Q%.2f%n", cargoDistancia);
        System.out.printf("Costo final: Q%.2f%n", costoFinal);
    }
}