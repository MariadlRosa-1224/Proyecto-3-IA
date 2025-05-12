import java.io.*;
import java.util.*;

public class BaseConocimiento {

    private List<ReglaDifusa> reglas; // Lista de reglas

    public BaseConocimiento() {
        this.reglas = new ArrayList<>();
    }

    // Método para cargar reglas desde un archivo
    public void cargarReglasDesdeArchivo(String archivo) throws FileNotFoundException {
        System.out.println("Intentando cargar el archivo: " + archivo);
        File file = new File(archivo);
    
        if (!file.exists()) {
            System.out.println("Archivo no encontrado.");
            return;
        }
    
        Scanner scanner = new Scanner(file);
    
        while (scanner.hasNextLine()) {
            String linea = scanner.nextLine();
            System.out.println("\nLínea leída: " + linea);
    
            // Eliminar espacios adicionales y dividir por espacios
            String[] partes = linea.trim().split("\\s+");
            System.out.println("Elementos encontrados (" + partes.length + "): " + Arrays.toString(partes));
    
            if (partes.length >= 8) {
                // Concatenar los elementos extra para formar el valor de salida
                StringBuilder valorSalida = new StringBuilder();
                for (int i = 7; i < partes.length; i++) {
                    valorSalida.append(partes[i]).append(" ");
                }
    
                // Eliminar el último espacio en blanco
                String valorFinal = valorSalida.toString().trim();
    
                // Corregimos aquí: pasamos el nombre de la variable de salida correctamente
                ReglaDifusa regla = new ReglaDifusa(
                    partes[0], partes[1],
                    partes[3], partes[4],
                    partes[2],
                    partes[6], // Aquí está el nombre correcto
                    valorFinal
                );
                reglas.add(regla);
            } else {
                System.out.println("La línea no tiene suficientes elementos, se ignorará.");
            }
        }
        scanner.close();
    }

    // Método para imprimir las reglas cargadas
    public void imprimirReglas() {
        if (reglas.isEmpty()) {
            System.out.println("No se han cargado reglas.");
        } else {
            for (ReglaDifusa regla : reglas) {
                System.out.println(regla.toString());
            }
        }
    }

    // Nuevo método para acceder a las reglas
    public List<ReglaDifusa> getReglas() {
        return reglas;
    }
}
