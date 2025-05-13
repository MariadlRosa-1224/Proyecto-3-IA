import java.util.ArrayList;
import java.util.List;

// Clase que representa una variable lingüística difusa
public class Variable {

    public String nombre; // Nombre de la variable
    public double universoMinimo; // Valor mínimo del universo
    public double universoMaximo; // Valor máximo del universo
    private List<ConjuntoDifuso> conjuntosDifusos; // Lista de conjuntos difusos asociados a esta variable

    // Constructor de la clase que recibe nombre, rango del universo y un arreglo de conjuntos difusos
    public Variable(String nombre, double universoMinimo, double universoMaximo, ConjuntoDifuso[] conjuntosDifusos) {
        this.nombre = nombre;
        this.universoMinimo = universoMinimo;
        this.universoMaximo = universoMaximo;
        this.conjuntosDifusos = new ArrayList<>();

        // Agrega cada conjunto difuso al atributo de la lista
        for (ConjuntoDifuso conjunto : conjuntosDifusos) {
            this.conjuntosDifusos.add(conjunto);
        }
    }

    // Getter para el nombre de la variable
    public String getNombre() {
        return nombre;
    }

    // Getter para el límite inferior del universo
    public double getRangoMin() {
        return universoMinimo;
    }

    // Getter para el límite superior del universo
    public double getRangoMax() {
        return universoMaximo;
    }

    // Retorna la lista de conjuntos difusos asociados a esta variable
    public List<ConjuntoDifuso> getConjuntosDifusos() {
        return conjuntosDifusos;
    }

    // Método para agregar un nuevo conjunto difuso a la lista
    public void agregarConjuntoDifuso(ConjuntoDifuso conjunto) {
        conjuntosDifusos.add(conjunto);
    }

    // Método de fuzificación: calcula el grado de pertenencia de un valor en cada conjunto
    public void fuzificar(double valor) {
        for (ConjuntoDifuso conjunto : conjuntosDifusos) {
            double pertenencia = conjunto.calcularGradoPertenencia(valor); // Calcula el grado
            conjunto.setGradoPertenencia(pertenencia); // Asigna el valor al conjunto
        }
    }

    // Busca un conjunto difuso por su nombre
    public ConjuntoDifuso getConjuntoPorNombre(String nombre) {
        return conjuntosDifusos.stream()
                .filter(conjunto -> conjunto.getNombre().equals(nombre)) // Filtra por nombre
                .findFirst() // Toma el primero encontrado
                .orElse(null); // Retorna null si no se encuentra
    }

    // Representación en texto de la variable y sus conjuntos
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Universo minimo: ").append(universoMinimo).append("\n");
        sb.append("Universo maximo: ").append(universoMaximo).append("\n");
        sb.append("Conjuntos difusos:\n");
        for (ConjuntoDifuso conjunto : conjuntosDifusos) {
            sb.append(conjunto.toString()).append("\n");
        }
        return sb.toString();
    }
}
