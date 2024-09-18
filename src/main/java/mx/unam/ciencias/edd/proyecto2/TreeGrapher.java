package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

public class TreeGrapher<T> { // Specify generic type T

    GraphicSVG grapher = new GraphicSVG();
    
    ArbolBinario<T> arbol;
    private Lines typeLine = Lines.NEITHER; 

    /**
     * Constructor único.
     */
    public TreeGrapher(ArbolBinario<T> arbol) {
        this.arbol = arbol;
    }

    public String graph(){
        int depth = arbol.altura();
        int x = (80 * (1 << depth) + 20 * ((1 << depth) - 1) + 20), y = 80;
        return grapher.initializeSVG(x, (80 * (depth + 1) + 20 * (depth) + 20) + 40) + treeGraph(x, y, depth) + grapher.finishSVG();
    }

    public String treeGraph(int x, int y, int depth){
        StringBuilder svg = new StringBuilder();

        graph(arbol.raiz(), x/2, y, svg, 0, x);

        return svg.toString();
    }

    private void graph(VerticeArbolBinario<T> vertice, int x, int y, StringBuilder svg, int izq, int der){
        if(vertice == null) return;

        if(vertice.hayDerecho()) svg.append(grapher.line(x, y, (x+der)/2, y + 100, typeLine));
        if(vertice.hayIzquierdo()) svg.append(grapher.line(x, y, (x+izq)/2, y + 100, typeLine));

        Color color = Color.NINGUNO;
        String c = "white";
        
        svg.append(graficaVertice(x,y, vertice));

        if(vertice.hayDerecho()) graph(vertice.derecho(), (x+der)/2, y + 100, svg, x, der);
        if(vertice.hayIzquierdo()) graph(vertice.izquierdo(), (x+izq)/2, y + 100, svg, izq, x);
    }

    protected String graficaVertice(int x, int y, VerticeArbolBinario<T> vertice) {
        return grapher.circle(x, y, "white",  vertice.toString(), 40);
    }

    protected boolean esDerecho(VerticeArbolBinario<T> vertice) {
        if (vertice == arbol.raiz())
            return false;

        if (!vertice.padre().hayDerecho())
            return false;

        return vertice.padre().derecho() == vertice;
    }

    protected boolean esIzquierdo(VerticeArbolBinario<T> vertice) {
        if (vertice == arbol.raiz())
            return false;

        if (!vertice.padre().hayIzquierdo())
            return false;

        return vertice.padre().izquierdo() == vertice;
    }

}