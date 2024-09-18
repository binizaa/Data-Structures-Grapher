
package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

public class TreeRedBlackGrapher<T extends Comparable<T>> extends TreeGrapher<T> {

    public TreeRedBlackGrapher(ArbolRojinegro<T> tree) {
        super(tree);
    }

    protected String graficaVertice( int x, int y, VerticeArbolBinario<T> vertice) {
        Color color = ((ArbolRojinegro<T>) arbol).getColor(vertice);
        String colorSVG = (color == Color.ROJO) ? "red" : "black";
        return grapher.circle(x, y, colorSVG, vertice.get().toString(), 40);
    }
    
}
