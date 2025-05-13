import java.util.ArrayList;
import java.util.List;

// Clase que representa el motor de inferencia de un sistema difuso
public class MotorInferencia {

    // Atributos: base de conocimiento y las variables de entrada y salida
    private BaseConocimiento baseConocimiento;
    private Variable entrada1;
    private Variable entrada2;
    private Variable salida;

    // Constructor que inicializa la base de conocimiento y las variables del sistema
    public MotorInferencia(BaseConocimiento bc, Variable e1, Variable e2, Variable s) {
        this.baseConocimiento = bc;
        this.entrada1 = e1;
        this.entrada2 = e2;
        this.salida = s;
    }

    // Método principal de inferencia: recibe los valores de entrada y retorna un valor difuso como salida
    public double inferir(double valorEntrada1, double valorEntrada2) {
        // Paso 1: Fuzzificación de las entradas (se calcula el grado de pertenencia de cada entrada a sus conjuntos)
        entrada1.fuzificar(valorEntrada1);
        entrada2.fuzificar(valorEntrada2);
    
        // Imprimir grados de pertenencia para la primera entrada (útil para depuración)
        System.out.println("=== Pertenencia " + entrada1.getNombre() + " ===");
        System.out.printf("Valor de Entrada 1: %.2f%n", valorEntrada1);
        for (ConjuntoDifuso conjunto : entrada1.getConjuntosDifusos()) {
            System.out.printf("%s : %.4f%n", conjunto.getNombre(), conjunto.getGradoPertenencia());
        }
        System.out.println();
    
        // Imprimir grados de pertenencia para la segunda entrada
        System.out.println("=== Pertenencia " + entrada2.getNombre() + " ===");
        System.out.printf("Valor de Entrada 2: %.2f%n", valorEntrada2);
        for (ConjuntoDifuso conjunto : entrada2.getConjuntosDifusos()) {
            System.out.printf("%s : %.4f%n", conjunto.getNombre(), conjunto.getGradoPertenencia());
        }
        System.out.println();
    
        // Paso 2: Evaluación de reglas y acumulación de contribuciones
        List<Double> contribuciones = new ArrayList<>(); // grados de activación de reglas
        List<Double> centroides = new ArrayList<>();     // centroides de los conjuntos de salida activados
    
        for (ReglaDifusa regla : baseConocimiento.getReglas()) {
            // Buscar los conjuntos difusos correspondientes a los nombres especificados en la regla
            ConjuntoDifuso conjuntoEntrada1 = entrada1.getConjuntoPorNombre(regla.getValorEntrada1());
            ConjuntoDifuso conjuntoEntrada2 = entrada2.getConjuntoPorNombre(regla.getValorEntrada2());
    
            // Verificar que ambos conjuntos existan
            if (conjuntoEntrada1 == null || conjuntoEntrada2 == null) {
                System.err.printf("Error: No se encontró alguno de los conjuntos en la regla: %s o %s%n",
                        regla.getValorEntrada1(), regla.getValorEntrada2());
                continue; // Saltar esta regla
            }
    
            // Aplicar el operador lógico: en este caso, usamos "AND" como mínimo entre los dos grados
            double grado1 = conjuntoEntrada1.getGradoPertenencia();
            double grado2 = conjuntoEntrada2.getGradoPertenencia();
            double gradoActivacion = Math.min(grado1, grado2);
    
            // Solo considerar reglas que tienen alguna activación (grado > 0)
            if (gradoActivacion > 0.0) {
                // Obtener el conjunto difuso de salida según la regla
                ConjuntoDifuso conjuntoSalida = salida.getConjuntoPorNombre(regla.getValorSalida());
                if (conjuntoSalida != null) {
                    double centroide = conjuntoSalida.getCentroide(); // obtener centroide del conjunto de salida
                    contribuciones.add(gradoActivacion); // guardar grado de activación
                    centroides.add(centroide);           // guardar centroide correspondiente
    
                    // Mostrar información sobre la regla activada
                    System.out.printf("Regla activada: [%s ∧ %s → %s] con grado %.4f y centroide %.2f%n",
                            regla.getValorEntrada1(),
                            regla.getValorEntrada2(),
                            regla.getValorSalida(),
                            gradoActivacion,
                            centroide);
                }
            }
        }
    
        // Paso 3: Agregación y des-fuzzificación (método del centroide ponderado)
        double numerador = 0.0;
        double denominador = 0.0;

        // Calcular el valor de salida como el promedio ponderado de los centroides
        for (int i = 0; i < contribuciones.size(); i++) {
            numerador += contribuciones.get(i) * centroides.get(i);
            denominador += contribuciones.get(i);
        }
    
        // Si ninguna regla fue activada, se retorna 0 como valor de salida
        if (denominador == 0.0) {
            System.out.println("Ninguna regla se activó, se retorna 0.");
            return 0.0;
        }
    
        // Valor final de salida difusa
        double salidaFinal = numerador / denominador;
        System.out.printf("Valor de salida (ventilación): %.2f%n", salidaFinal);
        return salidaFinal;
    }    
}
