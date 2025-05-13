// Esta clase representa un conjunto difuso
// Toma un conjunto de datos y usa la función de pertenencia para
// calcular el grado de pertenencia de cada elemento

import java.util.Arrays; 

public class ConjuntoDifuso {

    public String nombre;               // Nombre del conjunto difuso (ej: "bajo", "alto")
    private String tipo;               // Tipo de función de pertenencia: "triangular" o "trapezoidal"
    private double[] puntos;           // Puntos que definen la función (3 para triangular, 4 para trapezoidal)
    public double gradoPertenencia;    // Valor actual del grado de pertenencia (actualizable)

    // Constructor: inicializa el conjunto con su nombre, tipo y puntos
    public ConjuntoDifuso(String nombre, String tipo, double[] puntos) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.puntos = puntos;
        this.gradoPertenencia = 0.0; // Se inicializa en 0
    }

    // Getter del tipo de conjunto ("triangular", "trapezoidal", etc.)
    public String getTipo() {
        return tipo;
    }

    /**
     * Calcula el grado de pertenencia de un valor 'x' al conjunto difuso,
     * según el tipo de conjunto y los puntos definidos.
     */
    public double calcularGradoPertenencia(double x) {
        double grado = 0.0f;

        switch (tipo.toLowerCase()) {
            case "triangular":
                // Verifica si x está fuera del rango
                if (x < puntos[0] || x > puntos[2]) {
                    grado = 0.0;
                } else if (x == puntos[1]) {
                    grado = 1.0; // Máximo en el vértice del triángulo
                } else if (x < puntos[1]) {
                    grado = (x - puntos[0]) / (puntos[1] - puntos[0]); // Pendiente ascendente
                } else {
                    grado = (puntos[2] - x) / (puntos[2] - puntos[1]); // Pendiente descendente
                }
                break;

            case "trapezoidal":
                // Si los lados verticales colapsan en un punto
                if ((puntos[0] == puntos[1] && x == puntos[0]) ||
                    (puntos[2] == puntos[3] && x == puntos[3])) {
                    grado = 1.0;
                } else if (x <= puntos[0] || x >= puntos[3]) {
                    grado = 0.0; // Fuera del trapezoide
                } else if (x >= puntos[1] && x <= puntos[2]) {
                    grado = 1.0; // Parte superior plana
                } else if (x < puntos[1]) {
                    grado = (puntos[1] - puntos[0]) != 0 ?
                            (x - puntos[0]) / (puntos[1] - puntos[0]) : 0.0f; // Ascenso
                } else {
                    grado = (puntos[3] - puntos[2]) != 0 ?
                            (puntos[3] - x) / (puntos[3] - puntos[2]) : 0.0f; // Descenso
                }
                break;

            default:
                System.err.println("Tipo de conjunto no reconocido: " + tipo);
                grado = 0.0f;
        }

        return grado;
    }

    /**
     * Devuelve el centroide del conjunto:
     * - Para triangular: el punto medio del triángulo
     * - Para trapezoidal: el promedio de los puntos centrales
     */
    public double getCentroide() {
        if (puntos.length == 3) {  // Triangular
            return puntos[1];     // Vértice (máximo)
        } else if (puntos.length == 4) {  // Trapezoidal
            return (puntos[1] + puntos[2]) / 2.0; // Promedio de la parte plana
        }
        return 0.0; // Caso inválido
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public double[] getPuntos() {
        return puntos;
    }

    public double getGradoPertenencia() {
        return gradoPertenencia;
    }

    public void setGradoPertenencia(double valor) {
        this.gradoPertenencia = valor;
    }

    // Devuelve una representación en texto del conjunto
    @Override
    public String toString() {
        return "Nombre: " + nombre + "\n" +
               "Puntos: " + Arrays.toString(puntos) + "\n";
    }

}
