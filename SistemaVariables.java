import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

// Clase que representa un sistema que contiene un conjunto de variables lingüísticas
public class SistemaVariables {

    private List<Variable> variables; // Lista para almacenar las variables difusas

    // Constructor que inicializa la lista de variables
    public SistemaVariables() {
        this.variables = new ArrayList<>();
    }

    // Método para agregar una variable a la lista
    public void agregarVariable(Variable variable) {
        this.variables.add(variable);
    }

    // Método que devuelve la lista completa de variables
    public List<Variable> getVariables() {
        return variables;
    }

    // Método para obtener una variable específica por su nombre
    public Variable getVariable(String nombre) {
        for (Variable variable : variables) {
            if (variable.nombre.equals(nombre)) { // Se compara el nombre
                return variable; // Retorna la variable si se encuentra
            }
        }
        return null; // Si no se encuentra la variable, retorna null
    }

    // Método que lee un archivo de texto para cargar las variables y sus conjuntos difusos
    public void leerArchivo(String nombreArchivo) throws FileNotFoundException {
        String linea;
        BufferedReader br = new BufferedReader(new FileReader(nombreArchivo)); // Crea lector de archivo
    
        try {
            // Lee el archivo línea por línea
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(" ");
                if (partes.length == 3) { // Si la línea tiene 3 partes, es una definición de variable
                    String nombreVariable = partes[0];
                    double universoMinimo = Double.parseDouble(partes[1]); // Límite inferior del universo
                    double universoMaximo = Double.parseDouble(partes[2]); // Límite superior del universo
    
                    List<ConjuntoDifuso> conjuntosDifusos = new ArrayList<>();
    
                    // A continuación se leen los conjuntos difusos asociados a esta variable
                    while ((linea = br.readLine()) != null && !linea.trim().isEmpty()) {
                        String[] partesConjunto = linea.trim().split(" "); // Divide la línea
    
                        String nombreConjunto = partesConjunto[0]; // Nombre del conjunto difuso
                        String tipoFuncion = partesConjunto[1];    // Tipo de función (triangular, trapezoidal, etc.)
    
                        // Lee los puntos numéricos que definen la función de membresía
                        double[] puntos = new double[partesConjunto.length - 2];
                        for (int i = 2; i < partesConjunto.length; i++) {
                            puntos[i - 2] = Double.parseDouble(partesConjunto[i]);
                        }
    
                        // Crea el conjunto difuso y lo agrega a la lista
                        ConjuntoDifuso conjuntoDifuso = new ConjuntoDifuso(nombreConjunto, tipoFuncion, puntos);
                        conjuntosDifusos.add(conjuntoDifuso);
                    }
    
                    // Crea la variable con sus conjuntos y la agrega al sistema
                    Variable variable = new Variable(
                        nombreVariable,
                        universoMinimo,
                        universoMaximo,
                        conjuntosDifusos.toArray(new ConjuntoDifuso[0])
                    );
                    agregarVariable(variable);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Muestra errores si ocurren durante la lectura
        }
    }

    // Método que genera una representación en texto del sistema completo
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sistema de Variables:\n");
        for (Variable variable : variables) {
            sb.append(variable.toString()).append("\n"); // Agrega cada variable al string
        }
        return sb.toString(); // Retorna la representación completa
    }

    // Método que imprime la información de todas las variables en consola
    public void imprimirVariables() {
        for (Variable variable : variables) {
            System.out.println(variable.toString());
        }
    }
}
