import java.io.*;         
import java.util.*;       

public class BaseConocimiento {

    // Lista que almacenará todas las reglas difusas leídas del archivo
    private List<ReglaDifusa> reglas;

    // Constructor: Inicializa la lista de reglas como una ArrayList vacía
    public BaseConocimiento() {
        this.reglas = new ArrayList<>();
    }

    /**
     * Carga reglas desde un archivo de texto.
     * El formato esperado por línea es:
     * IF <var1> <valor1> AND <var2> <valor2> THEN <varSalida> <valorSalida>
     */
    public void cargarReglasDesdeArchivo(String archivo) throws FileNotFoundException {
        System.out.println("Intentando cargar el archivo: " + archivo);

        File file = new File(archivo);

        // Verifica si el archivo existe
        if (!file.exists()) {
            System.out.println("Archivo no encontrado.");
            return; // Sale del método si no encuentra el archivo
        }

        Scanner scanner = new Scanner(file); // Abre el archivo para lectura

        // Lee línea por línea el archivo
        while (scanner.hasNextLine()) {
            String linea = scanner.nextLine();
            System.out.println("\nLínea leída: " + linea);

            // Elimina espacios extra y divide la línea por espacios
            String[] partes = linea.trim().split("\\s+");
            System.out.println("Elementos encontrados (" + partes.length + "): " + Arrays.toString(partes));

            // Verifica que haya al menos 8 elementos para formar una regla válida
            if (partes.length >= 8) {
                // Reconstruye el valor de salida en caso de que esté formado por varias palabras
                StringBuilder valorSalida = new StringBuilder();
                for (int i = 7; i < partes.length; i++) {
                    valorSalida.append(partes[i]).append(" ");
                }

                // Elimina espacio final sobrante
                String valorFinal = valorSalida.toString().trim();

                /**
                 * Crea una nueva regla con los componentes:
                 */
                ReglaDifusa regla = new ReglaDifusa(
                    partes[0], partes[1],  // "IF" y variable 1
                    partes[3], partes[4],  // "AND" y variable 2
                    partes[2],             // valor lingüístico 1
                    partes[6],             // "THEN"
                    valorFinal             // valor lingüístico de salida
                );

                reglas.add(regla); // Agrega la regla a la lista
            } else {
                System.out.println("La línea no tiene suficientes elementos, se ignorará.");
            }
        }

        scanner.close(); // Cierra el archivo al terminar
    }

    // Muestra por consola todas las reglas cargadas
    public void imprimirReglas() {
        if (reglas.isEmpty()) {
            System.out.println("No se han cargado reglas.");
        } else {
            for (ReglaDifusa regla : reglas) {
                System.out.println(regla.toString()); // Usa el método toString de ReglaDifusa
            }
        }
    }

    // Devuelve la lista de reglas para que puedan ser usadas por otras clases (por ejemplo, el motor de inferencia)
    public List<ReglaDifusa> getReglas() {
        return reglas;
    }
}
