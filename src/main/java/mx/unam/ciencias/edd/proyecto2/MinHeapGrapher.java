package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

public class MinHeapGrapher<T extends Comparable<T>> extends TreeGrapher<T> {

    Lista<T> list;

    public MinHeapGrapher(Lista<T> lista) {
        super(new ArbolBinarioCompleto<T>(MonticuloMinimo.heapSort(lista)));
        this.list = MonticuloMinimo.heapSort(lista);
    } 

    public String graph(){
        StringBuilder str = new StringBuilder();
        int x = 10, y = 10;
        int elementos = 1;
        

        for(T element : list){
            str.append(grapher.rectangle(x, y, element.toString()));
            x += 80;
        }
        x+=40;
        int depth = arbol.altura();
        int ax = (80 * (1 << depth) + 20 * ((1 << depth) - 1) + 20), ay = 130;
        str.append(treeGraph(ax, ay, depth));

        return grapher.initializeSVG(ax > x ? ax:x, (80 * (depth + 1) + 40 * (depth) + 20) + 20) + str.toString() + grapher.finishSVG();  
    }
    
}