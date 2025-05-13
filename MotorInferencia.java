import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa el motor de inferencia difusa, que realiza el proceso de inferencia
 * utilizando una base de conocimiento y dos variables de entrada para obtener una salida difusa.
 */
public class MotorInferencia {

    // Atributos privados que representan la base de conocimiento y las variables involucradas
    private BaseConocimiento baseConocimiento;
    private Variable entrada1;
    private Variable entrada2;
    private Variable salida;

    /**
     * Constructor del motor de inferencia.
     * @param bc Base de conocimiento (conjunto de reglas difusas)
     * @param e1 Primera variable de entrada
     * @param e2 Segunda variable de entrada
     * @param s Variable de salida
     */
    public MotorInferencia(BaseConocimiento bc, Variable e1, Variable e2, Variable s) {
        this.baseConocimiento = bc;
        this.entrada1 = e1;
        this.entrada2 = e2;
        this.salida = s;
    }

    /**
     * Método principal de inferencia difusa.
     * @param valorEntrada1 Valor numérico real de la primera entrada
     * @param valorEntrada2 Valor numérico real de la segunda entrada
     * @return Valor numérico real de la salida calculada mediante inferencia difusa
     */
    public double inferir(double valorEntrada1, double valorEntrada2) {
        // Se realiza la fuzificación de los valores de entrada
        entrada1.fuzificar(valorEntrada1);
        entrada2.fuzificar(valorEntrada2);

        // Se imprimen los grados de pertenencia de la primera entrada
        System.out.println("=== Pertenencia " + entrada1.getNombre() + " ===");
        System.out.printf("Valor de Entrada 1: %.2f%n", valorEntrada1);
        for (ConjuntoDifuso conjunto : entrada1.getConjuntosDifusos()) {
            System.out.printf("%s : %.4f%n", conjunto.getNombre(), conjunto.getGradoPertenencia());
        }
        System.out.println();

        // Se imprimen los grados de pertenencia de la segunda entrada
        System.out.println("=== Pertenencia " + entrada2.getNombre() + " ===");
        System.out.printf("Valor de Entrada 2: %.2f%n", valorEntrada2);
        for (ConjuntoDifuso conjunto : entrada2.getConjuntosDifusos()) {
            System.out.printf("%s : %.4f%n", conjunto.getNombre(), conjunto.getGradoPertenencia());
        }
        System.out.println();

        // Listas para almacenar las contribuciones y los centroides correspondientes a las reglas activadas
        List<Double> contribuciones = new ArrayList<>();
        List<Double> centroides = new ArrayList<>();

        // Se recorren todas las reglas de la base de conocimiento
        for (ReglaDifusa regla : baseConocimiento.getReglas()) {
            // Se obtiene el conjunto difuso correspondiente a la entrada 1 según la regla
            ConjuntoDifuso conjuntoEntrada1 = entrada1.getConjuntoPorNombre(regla.getValorEntrada1());
            // Se obtiene el conjunto difuso correspondiente a la entrada 2 según la regla
            ConjuntoDifuso conjuntoEntrada2 = entrada2.getConjuntoPorNombre(regla.getValorEntrada2());

            // Verificación de que ambos conjuntos existan
            if (conjuntoEntrada1 == null || conjuntoEntrada2 == null) {
                System.err.printf("Error: No se encontró alguno de los conjuntos en la regla: %s o %s%n",
                        regla.getValorEntrada1(), regla.getValorEntrada2());
                continue; // Se omite esta regla si no se encontró alguno de los conjuntos
            }

            // Se obtiene el grado de pertenencia de cada entrada según su conjunto
            double grado1 = conjuntoEntrada1.getGradoPertenencia();
            double grado2 = conjuntoEntrada2.getGradoPertenencia();

            // Se determina el operador lógico (AND/OR) que se debe aplicar
            String operador = regla.getOperador();
            if (operador == null || operador.isBlank()) {
                // Si no está definido el operador, se usa una lógica por defecto
                if (grado1 > 0.7 || grado2 > 0.7) {
                    operador = "OR"; // Si alguno de los grados es alto, se aplica OR
                } else {
                    operador = "AND"; // Si ambos son bajos, se aplica AND
                }
            }

            // Se calcula el grado de activación de la regla
            double gradoActivacion = "OR".equalsIgnoreCase(operador)
                    ? Math.max(grado1, grado2) // OR → máximo
                    : Math.min(grado1, grado2); // AND → mínimo

            // Solo se consideran las reglas activadas (grado > 0)
            if (gradoActivacion > 0.0) {
                // Se obtiene el conjunto difuso de salida según la regla
                ConjuntoDifuso conjuntoSalida = salida.getConjuntoPorNombre(regla.getValorSalida());
                if (conjuntoSalida != null) {
                    // Se obtiene el centroide del conjunto de salida
                    double centroide = conjuntoSalida.getCentroide();

                    // Se almacenan la contribución y el centroide para el cálculo final
                    contribuciones.add(gradoActivacion);
                    centroides.add(centroide);

                    // Se imprime información sobre la regla activada
                    System.out.printf("Regla activada: [%s %s %s → %s] con grado %.4f y centroide %.2f%n",
                            regla.getValorEntrada1(),
                            operador.equals("AND") ? "∧" : "∨",
                            regla.getValorEntrada2(),
                            regla.getValorSalida(),
                            gradoActivacion,
                            centroide);
                }
            }
        }

        // Se calcula el valor de salida usando el método del centroide (promedio ponderado)
        double numerador = 0.0;
        double denominador = 0.0;

        for (int i = 0; i < contribuciones.size(); i++) {
            numerador += contribuciones.get(i) * centroides.get(i);
            denominador += contribuciones.get(i);
        }

        // Si ninguna regla se activó, se devuelve 0 como salida por defecto
        if (denominador == 0.0) {
            System.out.println("Ninguna regla se activó, se retorna 0.");
            return 0.0;
        }

        // Se calcula el valor final de la salida
        double salidaFinal = numerador / denominador;
        System.out.printf("Valor de salida (ventilación): %.2f%n", salidaFinal);
        return salidaFinal;
    }
}
