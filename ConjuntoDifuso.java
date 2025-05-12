

// Esta clase representa un conjunto difuso
// Toma un conjunto de datos y usa la funcion de pertenencia para
// calcular el grado de pertenencia de cada elemento

import java.util.Arrays;

public class ConjuntoDifuso {

    public String nombre;
    private String tipo;
    private double[] puntos; 
    public double gradoPertenencia;

    public ConjuntoDifuso(String nombre, String tipo, double[] puntos) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.puntos = puntos;
        this.gradoPertenencia = 0.0; // Inicializa el grado de pertenencia a 0
    }

    public String getTipo() {
        return tipo;
    }

    public double calcularGradoPertenencia(double x) {
        double grado = 0.0f;

        switch (tipo.toLowerCase()) {
            case "triangular":
                if (x < puntos[0] || x > puntos[2]) {
                    grado = 0.0;
                } else if (x == puntos[1]) {
                    grado = 1.0;
                } else if (x < puntos[1]) {
                    grado = (x - puntos[0]) / (puntos[1] - puntos[0]);
                } else {
                    grado = (puntos[2] - x) / (puntos[2] - puntos[1]);
                }
                break;

            case "trapezoidal":
                if ((puntos[0] == puntos[1] && x == puntos[0]) ||
                    (puntos[2] == puntos[3] && x == puntos[3])) {
                    grado = 1.0;
                } else if (x <= puntos[0] || x >= puntos[3]) {
                    grado = 0.0;
                } else if (x >= puntos[1] && x <= puntos[2]) {
                    grado = 1.0;
                } else if (x < puntos[1]) {
                    grado = (puntos[1] - puntos[0]) != 0 ?
                            (x - puntos[0]) / (puntos[1] - puntos[0]) : 0.0f;
                } else {
                    grado = (puntos[3] - puntos[2]) != 0 ?
                            (puntos[3] - x) / (puntos[3] - puntos[2]) : 0.0f;
                }
                break;

            default:
                System.err.println("Tipo de conjunto no reconocido: " + tipo);
                grado = 0.0f;
        }

        return grado;
    }

    public double getCentroide() {
        if (puntos.length == 3) {  // Triangular
            return puntos[1];  // El punto medio de la base
        } else if (puntos.length == 4) {  // Trapezoidal
            return (puntos[1] + puntos[2]) / 2;  // Promedio de los puntos internos
        }
        return 0.0;
    }


    // Getter para el nombre del conjunto difuso
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

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\n" +
            "Puntos: " + Arrays.toString(puntos) + "\n";
    }

    
}
