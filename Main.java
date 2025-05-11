import java.io.FileNotFoundException;

public class Main {

    static SistemaVariables sistema = new SistemaVariables();
    
    
    public static void main(String[] args) {
        System.out.println("Sistema de Inferencia Difusa");
        
        // Lectura de archivo de variables

        try {
            sistema.leerArchivo("variables.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return;
        }

        // imprimir variables

        sistema.imprimirVariables();

    }

    // El main incluye la validacion del motor de inferencia
}