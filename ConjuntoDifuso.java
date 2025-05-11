

// Esta clase representa un conjunto difuso
// Toma un conjunto de datos y usa la funcion de pertenencia para
// calcular el grado de pertenencia de cada elemento

public class ConjuntoDifuso {

    public String nombre;
    public float gradoPertenencia;
    public FuncionPertenencia funcionPertenencia;

    public ConjuntoDifuso(String nombre, FuncionPertenencia funcionPertenencia) {
        this.nombre = nombre;
        this.funcionPertenencia = funcionPertenencia;
        this.gradoPertenencia = 0.0f; // Inicializa el grado de pertenencia a 0
    }

    public void fuzzificar (float x) {
        // Calcula el grado de pertenencia
        this.gradoPertenencia = funcionPertenencia.calcularGradoPertenencia(x);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Grado de pertenencia: ").append(gradoPertenencia).append("\n");
        sb.append("Funcion de pertenencia: ").append(funcionPertenencia.toString()).append("\n");
        return sb.toString();
    }
    
}
