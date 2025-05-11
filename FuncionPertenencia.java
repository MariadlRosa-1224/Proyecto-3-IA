// Esta es una clase que representa una función de pertenencia 
//

public class FuncionPertenencia {
    public String tipo;
    public float[] puntos;

    // Constructor
    public FuncionPertenencia(String tipo, float[] puntos) {
        this.tipo = tipo;
        this.puntos = puntos;
    }

    // calcular grado de pertenencia
    public float calcularGradoPertenencia(float x) {
        float grado = 0.0f;
        switch (tipo) {
            case "triangular":
                if (x >= puntos[0] && x <= puntos[1]) {
                    grado = (x - puntos[0]) / (puntos[1] - puntos[0]);
                } else if (x > puntos[1] && x <= puntos[2]) {
                    grado = (puntos[2] - x) / (puntos[2] - puntos[1]);
                }
                break;
            case "trapezoidal":
                if (x >= puntos[0] && x <= puntos[1]) {
                    grado = 1.0f;
                } else if (x > puntos[1] && x <= puntos[2]) {
                    grado = (x - puntos[1]) / (puntos[2] - puntos[1]);
                } else if (x > puntos[2] && x <= puntos[3]) {
                    grado = (puntos[3] - x) / (puntos[3] - puntos[2]);
                }
                break;
            
        }
        return grado;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tipo: ").append(tipo).append("\n");
        sb.append("Puntos: ");
        for (float punto : puntos) {
            sb.append(punto).append(" ");
        }
        return sb.toString();
    }
}
