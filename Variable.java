import java.util.ArrayList;
import java.util.List;

public class Variable {
    public String nombre;
    public double universoMinimo;
    public double universoMaximo;
    private List<ConjuntoDifuso> conjuntosDifusos;

    public Variable(String nombre, double universoMinimo, double universoMaximo, ConjuntoDifuso[] conjuntosDifusos) {
        this.nombre = nombre;
        this.universoMinimo = universoMinimo;
        this.universoMaximo = universoMaximo;
        this.conjuntosDifusos = new ArrayList<>();

        // Agregamos los conjuntos difusos recibidos
        for (ConjuntoDifuso conjunto : conjuntosDifusos) {
            this.conjuntosDifusos.add(conjunto);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public double getRangoMin() {
        return universoMinimo;
    }

    public double getRangoMax() {
        return universoMaximo;
    }

    public List<ConjuntoDifuso> getConjuntosDifusos() {
        return conjuntosDifusos;
    }

    public void agregarConjuntoDifuso(ConjuntoDifuso conjunto) {
        conjuntosDifusos.add(conjunto);
    }

    public void fuzificar(double valor) {
        for (ConjuntoDifuso conjunto : conjuntosDifusos) {
            double pertenencia = conjunto.calcularGradoPertenencia(valor);
            conjunto.setGradoPertenencia(pertenencia);
        }
    }

    public ConjuntoDifuso getConjuntoPorNombre(String nombre) {
        return conjuntosDifusos.stream()
                .filter(conjunto -> conjunto.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }

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
