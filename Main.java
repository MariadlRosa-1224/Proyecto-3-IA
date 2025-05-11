import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        // Crear instancias de las clases de fase 1 y fase 2
        SistemaVariables sistemaVariables = new SistemaVariables();
        BaseConocimiento baseConocimiento = new BaseConocimiento();

        // Leer el archivo de variables
        try {
            sistemaVariables.leerArchivo("variables.txt");  // Asegúrate de tener el archivo variables.txt en la raíz
            sistemaVariables.imprimirVariables();  // Imprimir las variables cargadas
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de variables: " + e.getMessage());
        }

        // Cargar las reglas difusas
        try {
            baseConocimiento.cargarReglasDesdeArchivo("reglas.txt");  // Asegúrate de tener el archivo reglas.txt en la raíz
            baseConocimiento.imprimirReglas();  // Imprimir las reglas cargadas
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de reglas: " + e.getMessage());
        }

        // Aquí puedes agregar más lógica para probar la inferencia difusa si tienes datos de entrada específicos.
        // Puedes crear instancias de las variables y simular la aplicación de reglas con entradas difusas.
    }
}
