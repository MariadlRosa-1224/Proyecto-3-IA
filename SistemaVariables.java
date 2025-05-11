

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class SistemaVariables {

    private List<Variable> variables;

    // Esta funcion leera los datos de un archivo y los almacena en una lista


    public SistemaVariables() {
        this.variables = new ArrayList<>();

    }

    public void agregarVariable(Variable variable) {
        this.variables.add(variable);
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public Variable getVariable(String nombre) {
        for (Variable variable : variables) {
            if (variable.nombre.equals(nombre)) {
                return variable;
            }
        }
        return null; // Si no se encuentra la variable
    }


    public void leerArchivo(String nombreArchivo) throws FileNotFoundException {

        String linea;

        // lectura de archivo

        /* formato de archivo
            variable1 0 100
            clasificacion1 tipo 0 0 20 40
            clasificacion2 tipo 30 50 70
            clasificacion3 tipo 60 80 100 100

            variable2 0 100
            clasificacion1 tipo 0 0 20 40
            clasificacion2 tipo 30 50 70
            clasificacion3 tipo 60 80 100 100
         */ // Pueden haber mas clasificaciones rn cada variable y variar los puntoos



        BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));


        try {
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(" ");
                if (partes.length == 3) { // variable
                    String nombreVariable = partes[0];
                    float universoMinimo = Float.parseFloat(partes[1]);
                    float universoMaximo = Float.parseFloat(partes[2]);

                    List<ConjuntoDifuso> conjuntosDifusos = new ArrayList<>();

                    // Leer conjuntos difusos de la variable
                    while ((linea = br.readLine()) != null && !linea.isEmpty()) {
                        String[] partesConjunto = linea.split(" ");

                        String nombreConjunto = partesConjunto[0];

                        String tipoFuncion = partesConjunto[1];
                        
                        float[] puntos = new float[partesConjunto.length - 2];
                        for (int i = 2; i < partesConjunto.length; i++) {
                            puntos[i - 2] = Float.parseFloat(partesConjunto[i]);
                        }
                        FuncionPertenencia funcionPertenencia = new FuncionPertenencia(tipoFuncion, puntos);
                        ConjuntoDifuso conjuntoDifuso = new ConjuntoDifuso(nombreConjunto, funcionPertenencia);
                        conjuntosDifusos.add(conjuntoDifuso);
                    }
                    Variable variable = new Variable(nombreVariable, universoMinimo, universoMaximo, conjuntosDifusos.toArray(new ConjuntoDifuso[0]));
                    agregarVariable(variable);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sistema de Variables:\n");
        for (Variable variable : variables) {
            sb.append(variable.toString()).append("\n");
        }
        return sb.toString();
    }
    
    public void imprimirVariables() {
        for (Variable variable : variables) {
            System.out.println(variable.toString());
        }
    }
}
