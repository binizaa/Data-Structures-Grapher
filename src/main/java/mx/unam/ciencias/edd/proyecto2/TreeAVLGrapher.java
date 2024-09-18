package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

public class TreeAVLGrapher<T extends Comparable<T>> extends TreeGrapher<T> {

    public TreeAVLGrapher(ArbolAVL<T> arbol) {
        super(arbol);
    }

    protected String graficaVertice(int x, int y, VerticeArbolBinario<T> vertice) {
        String textoVertice = vertice.toString();
        int ultimoEspacio = textoVertice.lastIndexOf(' ');
        String balance = String.format("(%s)", textoVertice.substring(ultimoEspacio + 1));
        int centroTextoX = x;

        if (esDerecho(vertice)) centroTextoX += (int) Math.ceil(balance.length() / 2) * 20;
        else if (esIzquierdo(vertice)) centroTextoX += - (int) Math.ceil(balance.length() / 2) * 20;

        StringBuilder SVG = new StringBuilder();

        SVG.append(grapher.text(centroTextoX, y - 40 - 10,balance, "black", 20));
        SVG.append(grapher.circle(x, y, "white", vertice.get().toString(), 40));

        return SVG.toString();
    }
    
}
