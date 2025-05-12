// Clase que representa una regla difusa en el sistema de inferencia
public class ReglaDifusa {
    
    // Atributos de la regla
    private String variableEntrada1;
    private String conjuntoDifuso1;
    private String variableEntrada2;
    private String conjuntoDifuso2;
    private String operador;
    private String variableSalida;
    private String conjuntoSalida;

    // Constructor
    public ReglaDifusa(String variableEntrada1, String conjuntoDifuso1,
                       String variableEntrada2, String conjuntoDifuso2,
                       String operador, String variableSalida, String conjuntoSalida) {
        this.variableEntrada1 = variableEntrada1;
        this.conjuntoDifuso1 = conjuntoDifuso1;
        this.variableEntrada2 = variableEntrada2;
        this.conjuntoDifuso2 = conjuntoDifuso2;
        this.operador = operador;
        this.variableSalida = variableSalida;
        this.conjuntoSalida = conjuntoSalida;
    }

    // Método para visualizar la regla en formato legible
    @Override
    public String toString() {
        return "\nSI " + variableEntrada1 + " ES " + conjuntoDifuso1 + " " +
               operador + " " + variableEntrada2 + " ES " + conjuntoDifuso2 + 
               " ENTONCES " + variableSalida + " ES " + conjuntoSalida;
    }

    // Métodos getters necesarios
    public String getOperador() {
        return operador;
    }

    public String getValorSalida() {
        return conjuntoSalida;
    }

    public String getValorEntrada1() {
        return conjuntoDifuso1;
    }

    public String getValorEntrada2() {
        return conjuntoDifuso2;
    }
}
