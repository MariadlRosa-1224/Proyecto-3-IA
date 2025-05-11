// Una variable se representa de varios conjuntos difusos

public class Variable {
    public String nombre;
    public float universoMinimo;
    public float universoMaximo;
    public ConjuntoDifuso[] conjuntosDifusos;

    public Variable(String nombre, float universoMinimo, float universoMaximo, ConjuntoDifuso[] conjuntosDifusos) {
        this.nombre = nombre;
        this.universoMinimo = universoMinimo;
        this.universoMaximo = universoMaximo;
        this.conjuntosDifusos = conjuntosDifusos;
    }

    public void fuzzificar(float x) {
        for (ConjuntoDifuso conjunto : conjuntosDifusos) {
            conjunto.fuzzificar(x);
        }
    }

    public float getGradoPertenencia(String nombreConjunto) {
        for (ConjuntoDifuso conjunto : conjuntosDifusos) {
            if (conjunto.nombre.equals(nombreConjunto)) {
                return conjunto.gradoPertenencia;
            }
        }
        return -1; // Si no se encuentra el conjunto
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
