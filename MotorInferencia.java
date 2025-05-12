import java.util.ArrayList;
import java.util.List;

public class MotorInferencia {

    private BaseConocimiento baseConocimiento;
    private Variable entrada1;
    private Variable entrada2;
    private Variable salida;

    public MotorInferencia(BaseConocimiento bc, Variable e1,
                             Variable e2, Variable s) {
        this.baseConocimiento = bc;
        this.entrada1 = e1;
        this.entrada2 = e2;
        this.salida = s;
    }

    public double inferir(double valorEntrada1, double valorEntrada2) {
        // Fuzificación
        entrada1.fuzificar(valorEntrada1);
        entrada2.fuzificar(valorEntrada2);

        // Imprimir pertenencias de Entrada 1
        System.out.println("=== Pertenencia Temperatura ===");
        System.out.printf("Valor de Entrada 1: %.2f%n", valorEntrada1);
        for (ConjuntoDifuso conjunto : entrada1.getConjuntosDifusos()) {
            System.out.printf("%s : %.4f%n", conjunto.getNombre(), conjunto.getGradoPertenencia());
        }
        System.out.println();

        // Imprimir pertenencias de Entrada 2
        System.out.println("=== Pertenencia Humedad ===");
        System.out.printf("Valor de Entrada 2: %.2f%n", valorEntrada2);
        for (ConjuntoDifuso conjunto : entrada2.getConjuntosDifusos()) {
            System.out.printf("%s : %.4f%n", conjunto.getNombre(), conjunto.getGradoPertenencia());
        }
        System.out.println();

        // Evaluación de reglas y agregación
        List<Double> fuerzaReglas = new ArrayList<>();
        List<String> conjuntosSalida = new ArrayList<>();

        for (ReglaDifusa regla : baseConocimiento.getReglas()) {
            // Verifica y obtiene conjuntos de antecedente
            ConjuntoDifuso conjunto1 = entrada1.getConjuntoPorNombre(regla.getValorEntrada1());
            ConjuntoDifuso conjunto2 = entrada2.getConjuntoPorNombre(regla.getValorEntrada2());

            if (conjunto1 == null || conjunto2 == null) {
                System.err.printf("Error: Algún conjunto no se encontró en las entradas: %s o %s%n", regla.getValorEntrada1(), regla.getValorEntrada2());
                continue;
            }

            double fuerza1 = conjunto1.getGradoPertenencia();
            double fuerza2 = conjunto2.getGradoPertenencia();
            double fuerzaRegla = Math.min(fuerza1, fuerza2);

            // Solo agregar si la fuerza es significativa
            if (fuerzaRegla > 0) {
                fuerzaReglas.add(fuerzaRegla);
                conjuntosSalida.add(regla.getValorSalida());
                //System.out.printf("Fuerza de la regla: %f para salida %s%n", fuerzaRegla, regla.getValorSalida());
            }
        }

        // Defuzzificación
        return calcularCentroide(fuerzaReglas, conjuntosSalida);
    }

    private double calcularCentroide(List<Double> fuerzas, List<String> conjuntos) {
        double numerador = 0.0;
        double denominador = 0.0;

        for (int i = 0; i < fuerzas.size(); i++) {
            ConjuntoDifuso conjunto = salida.getConjuntoPorNombre(conjuntos.get(i));
            if (conjunto != null) {
                double centroide = conjunto.getCentroide(); // Asegúrate que este método esté implementado
                numerador += centroide * fuerzas.get(i);
                denominador += fuerzas.get(i);
            }
        }

        return (denominador == 0) ? 0 : numerador / denominador;
    }
}

    
