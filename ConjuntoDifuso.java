import java.util.Arrays;

/**
 * Esta clase representa un conjunto difuso, el cual puede tener una función de membresía
 * de tipo triangular o trapezoidal. Permite calcular el grado de pertenencia para un valor dado.
 */
public class ConjuntoDifuso {

    private String nombre;             // Nombre del conjunto difuso (por ejemplo: "bajo", "medio", "alto")
    private String tipo;              // Tipo de función de membresía: "triangular" o "trapezoidal"
    private double[] puntos;          // Puntos que definen la forma del conjunto (3 para triangular, 4 para trapezoidal)
    private double gradoPertenencia;  // Último grado de pertenencia calculado para este conjunto

    /**
     * Constructor del conjunto difuso.
     * @param nombre Nombre del conjunto (por ejemplo, "bajo")
     * @param tipo Tipo de función ("triangular" o "trapezoidal")
     * @param puntos Puntos que definen la forma (3 para triangular, 4 para trapezoidal)
     */
    public ConjuntoDifuso(String nombre, String tipo, double[] puntos) {
        this.nombre = nombre;
        this.tipo = tipo.toLowerCase();  // Se normaliza el tipo a minúsculas
        this.puntos = puntos.clone();    // Se clona el arreglo para evitar aliasing
        this.gradoPertenencia = 0.0;     // Inicialmente el grado es 0
    }

    /**
     * Calcula el grado de pertenencia de un valor x a este conjunto.
     * @param x Valor numérico al que se desea calcular el grado de pertenencia
     * @return Grado de pertenencia entre 0 y 1
     */
    public double calcularGradoPertenencia(double x) {
        double grado = 0.0;

        switch (tipo) {
            case "triangular":
                // Verifica que haya 3 puntos
                if (puntos.length != 3) {
                    System.err.println("Error: función triangular requiere 3 puntos.");
                    break;
                }

                // Calcula el grado de pertenencia para la forma triangular
                if (x <= puntos[0] || x >= puntos[2]) {
                    grado = 0.0; // Fuera de la base del triángulo
                } else if (x == puntos[1]) {
                    grado = 1.0; // En el pico del triángulo
                } else if (x < puntos[1]) {
                    grado = (x - puntos[0]) / (puntos[1] - puntos[0]); // Pendiente ascendente
                } else {
                    grado = (puntos[2] - x) / (puntos[2] - puntos[1]); // Pendiente descendente
                }
                break;

            case "trapezoidal":
                // Verifica que haya 4 puntos
                if (puntos.length != 4) {
                    System.err.println("Error: función trapezoidal requiere 4 puntos.");
                    break;
                }

                // Calcula el grado de pertenencia para la forma trapezoidal
                if (x <= puntos[0] || x >= puntos[3]) {
                    grado = 0.0; // Fuera de los bordes
                } else if (x >= puntos[1] && x <= puntos[2]) {
                    grado = 1.0; // Parte superior plana del trapecio
                } else if (x < puntos[1]) {
                    // Pendiente ascendente, evita división por cero
                    grado = (puntos[1] - puntos[0]) != 0 ? (x - puntos[0]) / (puntos[1] - puntos[0]) : 0.0;
                } else {
                    // Pendiente descendente, evita división por cero
                    grado = (puntos[3] - puntos[2]) != 0 ? (puntos[3] - x) / (puntos[3] - puntos[2]) : 0.0;
                }
                break;

            default:
                // Tipo de conjunto desconocido
                System.err.println("Tipo de conjunto no reconocido: " + tipo);
        }

        this.gradoPertenencia = grado; // Guarda el valor calculado internamente
        return grado;
    }

    /**
     * Calcula el centroide del conjunto, usado comúnmente en la defuzzificación.
     * @return Valor del centroide del conjunto
     */
    public double getCentroide() {
        if (tipo.equals("triangular") && puntos.length == 3) {
            return puntos[1]; // El pico del triángulo
        } else if (tipo.equals("trapezoidal") && puntos.length == 4) {
            return (puntos[1] + puntos[2]) / 2.0; // Promedio de la base superior
        }
        return 0.0; // Valor por defecto en caso de error
    }

    // Métodos getter y setter

    public String getNombre() {
        return nombre;
    }

    public double[] getPuntos() {
        return puntos.clone(); // Se devuelve una copia para evitar cambios externos
    }

    public double getGradoPertenencia() {
        return gradoPertenencia;
    }

    public void setGradoPertenencia(double valor) {
        this.gradoPertenencia = valor;
    }

    /**
     * Método toString para imprimir información del conjunto difuso.
     * @return Cadena con el nombre y los puntos del conjunto
     */
    @Override
    public String toString() {
        return "Nombre: " + nombre + "\n" +
               "Puntos: " + Arrays.toString(puntos) + "\n";
    }
}
