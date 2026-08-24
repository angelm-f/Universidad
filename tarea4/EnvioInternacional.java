package tarea4;

public class EnvioInternacional extends Envio {

    private String pais;

    public EnvioInternacional(String codigo, String destinatario, double peso,
                              String pais) {

        super(codigo, destinatario, peso);

        this.pais = pais;
    }

    public String getPais() {
        return pais;
    }

    @Override
    public double calcularCostoFinal() {
        double costoBase = calcularCostoBase();
        double recargo = costoBase * 0.12;

        return costoBase + 75.00 + recargo;
    }

    @Override
    protected void mostrarDesglose() {
        double costoBase = calcularCostoBase();
        double recargo = costoBase * 0.12;
        double cargoGestion = 75.00;
        double costoFinal = calcularCostoFinal();

        System.out.println("===== DESGLOSE ENVÍO INTERNACIONAL =====");
        System.out.println("Código: " + getCodigo());
        System.out.println("Destinatario: " + getDestinatario());
        System.out.println("País: " + pais);
        System.out.println("Peso: " + getPeso() + " kg");

        System.out.printf("Costo base: Q%.2f%n", costoBase);
        System.out.printf("Cargo internacional: Q%.2f%n", cargoGestion);
        System.out.printf("Recargo del 12%%: Q%.2f%n", recargo);
        System.out.printf("Costo final: Q%.2f%n", costoFinal);
    }
}