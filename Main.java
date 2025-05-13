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

        try {
            Variable temperatura = sistemaVariables.getVariable("temperatura");
            Variable humedad = sistemaVariables.getVariable("humedad");
            Variable ventilacion = sistemaVariables.getVariable("ventilacion");
            
            if (temperatura == null || humedad == null || ventilacion == null) {
                System.out.println("Error: Una o más variables no fueron encontradas en el archivo.");
                return;
            }
            
            MotorInferencia inferencia = new MotorInferencia(
                    baseConocimiento, temperatura, humedad, ventilacion);
        
            System.out.println("\n--- PRUEBA DEL SISTEMA ---");
        
            double valorTemperatura = 45;
            double valorHumedad = 50;

            // Resultado Temperatura (85) y Humedad (20)
        
            System.out.printf("Entrada: temperatura = %.2f, humedad = %.2f%n", 
                valorTemperatura, valorHumedad);
        
            double resultadoVentilacion = inferencia.inferir(valorTemperatura, valorHumedad);
        
            System.out.printf("Resultado (ventilación): %.2f%n", resultadoVentilacion);
        
        } catch (Exception e) {
            System.out.println("Error durante la ejecución: " + e.getMessage());
            e.printStackTrace();
        }        
        
    }
}
