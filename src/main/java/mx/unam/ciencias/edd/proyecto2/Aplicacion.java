package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Aplicacion {

    private String[] args;

    public Aplicacion(String[] args) {
        this.args = args;
    }

    public void ejecuta() {

        Entrada entrada = new Entrada(args);

        if (args.length == 0) {
            entrada.estandar();
        } else {
            entrada.entParametros();
        }

        Estructuras estructura = Estructuras.getEstructura(entrada.getEstructura());
        Lista<Integer> elementos = entrada.getElementos();
        String svgGraph;

        switch (estructura) {
            case LISTA :
                Lines type = Lines.BOTH;
                ListGrapher<Integer> listGrapher = new ListGrapher<>(elementos, type);                
                System.out.println(listGrapher.graph());
                break;
            case COLA :
                QueueGrapher<Integer> queueGrapher = new QueueGrapher<>(elementos);                
                System.out.println(queueGrapher.graph());
                break;
            case PILA :
                StackGrapher<Integer> stackGrapher = new StackGrapher<>(elementos.reversa());                
                System.out.println(stackGrapher.graph());
                break;
            case ARBOL_BINARIO_COMPLETO :
                TreeGrapher<Integer> treeGrapher = new TreeGrapher<>(new ArbolBinarioCompleto<>(elementos));                
                System.out.println(treeGrapher.graph());
                break;
            case ARBOL_BINARIO_ORDENADO :
                TreeGrapher<Integer> treeOGrapher = new TreeGrapher<>(new ArbolBinarioOrdenado<>(elementos));                
                System.out.println(treeOGrapher.graph());
                break;
            case ARBOL_ROJINEGRO :
                TreeRedBlackGrapher<Integer> treeRGrapher = new TreeRedBlackGrapher<>(new ArbolRojinegro<>(elementos));                
                System.out.println(treeRGrapher.graph());
                break;
            case ARBOL_AVL :
                TreeAVLGrapher<Integer> treeAGrapher = new TreeAVLGrapher<>(new ArbolAVL<>(elementos));                
                System.out.println(treeAGrapher.graph());
                break;
            case GRAFICA :
                GraphGrapher<Integer> Grapher = new GraphGrapher<>(elementos);                
                System.out.println(Grapher.graph());
                break;
            case MONTICULO_MINIMO :
                MinHeapGrapher<Integer> MinGrapher = new MinHeapGrapher<>(elementos);                
                System.out.println(MinGrapher.graph());
                break;
            default:
                System.out.println("Estructura no válida");
                break;
        }
    }

    /**
     * Método para guardar un string SVG en un archivo.
     * 
     * @param svgString     El string SVG a guardar.
     * @param nombreArchivo El nombre del archivo donde se guardará el SVG.
     */
    private void guardarSVGEnArchivo(String svgString, String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write(svgString);
            System.out.println("Se ha guardado el gráfico SVG en el archivo: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }


}
