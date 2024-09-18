package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

public class GraphGrapher<T> {


    private class Coordenada {
        public int x;
        public int y;
        public T elemento;
        public int index; 

        public Coordenada(int x, int y, int index, T elemento) {
            this.x = x;
            this.y = y;
            this.index = index;
            this.elemento = elemento;
        }
    }

    private Grafica<T> grafica;

    private Lista<Coordenada> verticesGraficados = new Lista<>();

    GraphicSVG grapher = new GraphicSVG();

    public GraphGrapher(Lista<T> grafica) {
        this.grafica = build(grafica);
    }

    public String graph() {
    
        double angulo = 360 / grafica.getElementos();
        double anguloAcumulado = 0;
        int index = 0;
        int radio = (int) Math.round(radioTotal + 50);
        double radioTotal = Math.abs(120 / (2 * Math.sin(Math.toRadians(angulo / 2))));

        Lista<VerticeGrafica<T>> vertices = new Lista<>();
        grafica.paraCadaVertice((vertice) -> vertices.agrega(vertice));

        StringBuilder edges = new StringBuilder();
        StringBuilder puntos = new StringBuilder();

        for (VerticeGrafica<T> vertice : vertices) {

            int punto1 = (int) Math.round(radioTotal * Math.cos(Math.toRadians(anguloAcumulado))) + radio;
            int punto2 = (int) Math.round(radioTotal * Math.sin(Math.toRadians(anguloAcumulado))) + radio;

            puntos.append(grapher.circle(punto1, punto2, "white", vertice.get().toString(), 40));
            Coordenada coord = new Coordenada(punto1, punto2, index++, vertice.get());

            for (VerticeGrafica<T> neighbor : vertice.vecinos()) {
                Coordenada coordenadaV = getCoordenada(neighbor, verticesGraficados);
                if (coordenadaV != null) edges.append(edgesGrapher(coord, coordenadaV, radio));
            }

            verticesGraficados.agrega(coord);
            anguloAcumulado += angulo;
        }

        return grapher.initializeSVG(radio * 2, radio * 2) + edges.toString() + puntos.toString() + grapher.finishSVG();
    }

    private <T> Grafica<T> build(Lista<T> lista){
        if (lista.getElementos() % 2 != 0) throw new IllegalArgumentException("Los elementos deben ser pares para construir una gráfica.");
    
        Grafica<T> grafica = new Grafica<>();
    
        for (T vertex : lista) if (!grafica.contiene(vertex)) grafica.agrega(vertex);
    
        for (int i = 0; i < lista.getElementos(); i += 2) {
            T p1 = lista.get(i);
            T p2 = lista.get(i + 1);
    
            if (p1.equals(p2)) continue;
            if(!grafica.sonVecinos(p1, p2)) grafica.conecta(p1, p2);
        }
    
        return grafica;
    }

    private String edgesGrapher(Coordenada p1, Coordenada p2, int radio) {
        if (Math.abs(p1.index - p2.index) == 1) return grapher.line(p1.x, p1.y, p2.x, p2.y, Lines.NEITHER);
        return grapher.curva(p1.x, p1.y, p2.x - p1.x, p2.y - p1.y, radio - p1.x, radio - p1.y);
    }
      
    private Coordenada getCoordenada(VerticeGrafica<T> vertice, Lista<Coordenada> coordenadas) {
        for (Coordenada coordenada : coordenadas) if (coordenada.elemento.equals(vertice.get())) return coordenada;
        return null;
    }
    
}
