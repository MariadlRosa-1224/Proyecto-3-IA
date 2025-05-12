import java.util.ArrayList;
import java.util.List;

public class MotorInferencia {

    private BaseConocimiento baseConocimiento;
    private Variable entrada1;
    private Variable entrada2;
    private Variable salida;

    public MotorInferencia(BaseConocimiento bc, Variable e1, Variable e2, Variable s) {
        this.baseConocimiento = bc;
        this.entrada1 = e1;
        this.entrada2 = e2;
        this.salida = s;
    }

    public double inferir(double valorEntrada1, double valorEntrada2) {
        // Fuzzificación de las entradas
        entrada1.fuzificar(valorEntrada1);
        entrada2.fuzificar(valorEntrada2);
    
        // Imprimir los grados de pertenencia para seguimiento
        System.out.println("=== Pertenencia " + entrada1.getNombre() + " ===");
        System.out.printf("Valor de Entrada 1: %.2f%n", valorEntrada1);
        for (ConjuntoDifuso conjunto : entrada1.getConjuntosDifusos()) {
            System.out.printf("%s : %.4f%n", conjunto.getNombre(), conjunto.getGradoPertenencia());
        }
        System.out.println();
    
        System.out.println("=== Pertenencia " + entrada2.getNombre() + " ===");
        System.out.printf("Valor de Entrada 2: %.2f%n", valorEntrada2);
        for (ConjuntoDifuso conjunto : entrada2.getConjuntosDifusos()) {
            System.out.printf("%s : %.4f%n", conjunto.getNombre(), conjunto.getGradoPertenencia());
        }
        System.out.println();
    
        // Evaluación de reglas y recopilación de contribuciones
        List<Double> contribuciones = new ArrayList<>();
        List<Double> centroides = new ArrayList<>();
    
        for (ReglaDifusa regla : baseConocimiento.getReglas()) {
            // Obtener los conjuntos difusos de las entradas usando los nombres especificados en la regla
            ConjuntoDifuso conjuntoEntrada1 = entrada1.getConjuntoPorNombre(regla.getValorEntrada1());
            ConjuntoDifuso conjuntoEntrada2 = entrada2.getConjuntoPorNombre(regla.getValorEntrada2());
    
            if (conjuntoEntrada1 == null || conjuntoEntrada2 == null) {
                System.err.printf("Error: No se encontró alguno de los conjuntos en la regla: %s o %s%n",
                        regla.getValorEntrada1(), regla.getValorEntrada2());
                continue;
            }
    
            // Operador AND: se utiliza el mínimo de los dos grados de pertenencia
            double grado1 = conjuntoEntrada1.getGradoPertenencia();
            double grado2 = conjuntoEntrada2.getGradoPertenencia();
            double gradoActivacion = Math.min(grado1, grado2);
    
            // Solo se consideran las reglas que aporten (grado > 0)
            if (gradoActivacion > 0.0) {
                // Obtener el conjunto de salida relacionado con la regla
                ConjuntoDifuso conjuntoSalida = salida.getConjuntoPorNombre(regla.getValorSalida());
                if (conjuntoSalida != null) {
                    double centroide = conjuntoSalida.getCentroide();
                    contribuciones.add(gradoActivacion);
                    centroides.add(centroide);
    
                    System.out.printf("Regla activada: [%s ∧ %s → %s] con grado %.4f y centroide %.2f%n",
                            regla.getValorEntrada1(),
                            regla.getValorEntrada2(),
                            regla.getValorSalida(),
                            gradoActivacion,
                            centroide);
                }
            }
        }
    
        // Agregación y des-fuzzificación mediante el centroide ponderado
        double numerador = 0.0;
        double denominador = 0.0;
        for (int i = 0; i < contribuciones.size(); i++) {
            numerador += contribuciones.get(i) * centroides.get(i);
            denominador += contribuciones.get(i);
        }
    
        if (denominador == 0.0) {
            System.out.println("Ninguna regla se activó, se retorna 0.");
            return 0.0;
        }
    
        double salidaFinal = numerador / denominador;
        System.out.printf("Valor de salida (ventilación): %.2f%n", salidaFinal);
        return salidaFinal;
    }    
}
