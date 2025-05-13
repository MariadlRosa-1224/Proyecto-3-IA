// Clase que representa una regla difusa en el sistema de inferencia
public class ReglaDifusa {
    
    // Atributos de la regla: dos variables de entrada con sus conjuntos difusos, un operador lógico,
    // y una variable de salida con su conjunto difuso correspondiente
    private String variableEntrada1;
    private String conjuntoDifuso1;
    private String variableEntrada2;
    private String conjuntoDifuso2;
    private String operador;
    private String variableSalida;
    private String conjuntoSalida;

    // Constructor que inicializa todos los atributos de la regla
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

    // Método que retorna una representación legible de la regla en formato tipo "SI ... ENTONCES ..."
    @Override
    public String toString() {
        return "\nSI " + variableEntrada1 + " ES " + conjuntoDifuso1 + " " +
               operador + " " + variableEntrada2 + " ES " + conjuntoDifuso2 + 
               " ENTONCES " + variableSalida + " ES " + conjuntoSalida;
    }

    // Getter que retorna el operador lógico de la regla (por ejemplo: AND, OR)
    public String getOperador() {
        return operador;
    }

    // Getter que retorna el conjunto difuso asociado a la variable de salida
    public String getValorSalida() {
        return conjuntoSalida;
    }

    // Getter que retorna el conjunto difuso asociado a la primera variable de entrada
    public String getValorEntrada1() {
        return conjuntoDifuso1;
    }

    // Getter que retorna el conjunto difuso asociado a la segunda variable de entrada
    public String getValorEntrada2() {
        return conjuntoDifuso2;
    }
}
