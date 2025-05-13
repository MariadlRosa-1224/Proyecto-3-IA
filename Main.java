import java.io.FileNotFoundException; 

public class Main {

    public static void main(String[] args) {

        // Crear una instancia del sistema que maneja las variables difusas definidas en el archivo
        SistemaVariables sistemaVariables = new SistemaVariables();

        // Crear una instancia de la base de conocimiento que contendrá las reglas difusas
        BaseConocimiento baseConocimiento = new BaseConocimiento();

        // Intentar leer el archivo "variables.txt" que contiene la definición de las variables difusas
        try {
            sistemaVariables.leerArchivo("variables.txt");  // Lee el archivo de variables
            sistemaVariables.imprimirVariables();  // Muestra por consola las variables cargadas correctamente
        } catch (FileNotFoundException e) {
            // Si el archivo no se encuentra, mostrar un mensaje de error
            System.out.println("No se encontró el archivo de variables: " + e.getMessage());
        }

        // Intentar leer el archivo "reglas.txt" que contiene las reglas difusas del sistema
        try {
            baseConocimiento.cargarReglasDesdeArchivo("reglas.txt");  // Carga las reglas del sistema
            baseConocimiento.imprimirReglas();  // Muestra por consola las reglas cargadas correctamente
        } catch (FileNotFoundException e) {
            // Si el archivo no se encuentra, mostrar un mensaje de error
            System.out.println("No se encontró el archivo de reglas: " + e.getMessage());
        }

        // Realiza una prueba del sistema utilizando valores de entrada para temperatura y humedad
        try {
            // Obtener las variables necesarias desde el sistema
            Variable temperatura = sistemaVariables.getVariable("temperatura");
            Variable humedad = sistemaVariables.getVariable("humedad");
            Variable ventilacion = sistemaVariables.getVariable("ventilacion");

            // Verificar que todas las variables requeridas hayan sido cargadas correctamente
            if (temperatura == null || humedad == null || ventilacion == null) {
                System.out.println("Error: Una o más variables no fueron encontradas en el archivo.");
                return;  // Termina la ejecución si falta alguna variable
            }

            // Crear el motor de inferencia, que es responsable de aplicar las reglas y calcular la salida
            MotorInferencia inferencia = new MotorInferencia(
                    baseConocimiento, temperatura, humedad, ventilacion);

            System.out.println("\n--- PRUEBA DEL SISTEMA ---");

            // Definir valores de entrada (crisp) para las variables de entrada
            double valorTemperatura = 45;
            double valorHumedad = 80;

            // Mostrar los valores de entrada
            System.out.printf("Entrada: temperatura = %.2f, humedad = %.2f%n", 
                valorTemperatura, valorHumedad);

            // Aplicar el sistema difuso para obtener el resultado de ventilación
            double resultadoVentilacion = inferencia.inferir(valorTemperatura, valorHumedad);

            // Mostrar el resultado final (valor difuso para la salida)
            System.out.printf("Resultado (ventilación): %.2f%n", resultadoVentilacion);

        } catch (Exception e) {
            // En caso de cualquier otro error durante la ejecución, imprimir la excepción
            System.out.println("Error durante la ejecución: " + e.getMessage());
            e.printStackTrace();
        }        
    }
}
