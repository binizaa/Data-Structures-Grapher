package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        private Iterador() {
            cola = new Cola<>();
            if(!esVacia()) cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !cola.esVacia();
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
            Vertice v = cola.saca();
            if(v.hayIzquierdo()) cola.mete(v.izquierdo);
            if(v.hayDerecho()) cola.mete(v.derecho);
            return v.elemento;
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("El elemento es nulo.");

        Vertice nuevo = nuevoVertice(elemento);
        elementos++;

        if (raiz == null) raiz = nuevo;
        else {
            Vertice vertice = vertice(verticeVacio());
            nuevo.padre = vertice;

            if (vertice.izquierdo == null)
                vertice.izquierdo = nuevo;
            else
                vertice.derecho = nuevo;
        }
    }

    private VerticeArbolBinario<T> verticeVacio(){
        Pila<Boolean> binario = new Pila<>();
        int cantidad = elementos;
        Vertice v, aux;
        v = aux = raiz;
        
        while(cantidad > 1){
            binario.mete((cantidad & 1) == 0);
            cantidad >>= 1;
        }

        while(aux !=null){
            v = aux;
            aux = binario.saca() ? aux.izquierdo : aux.derecho;
        }

        return v;
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Vertice v = vertice(busca(elemento));

        if(elementos == 1) limpia();
        else{
            Vertice ultimo = vertice(ultimo());
            v.elemento = ultimo.elemento;

            if (ultimo.padre.derecho == ultimo) ultimo.padre.derecho = null;
            else ultimo.padre.izquierdo = null;

            elementos--;
        }
    }

    private VerticeArbolBinario<T> ultimo() {
        Cola<Vertice> cola = new Cola<Vertice>();
        cola.mete(raiz);

        Vertice ultimo = raiz;

        while (!cola.esVacia()) {
            Vertice actual = cola.saca();
            ultimo = actual;

            if (actual.hayIzquierdo()) cola.mete(actual.izquierdo);
            if (actual.hayDerecho()) cola.mete(actual.derecho);
        }

        return ultimo;
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        if(esVacia()) return -1;
        
        int altura = 0;
        int x = elementos;

        while (x > 1) {
            x >>= 1; 
            altura++; 
        }

        return altura;
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        if(esVacia()) return;
        
        Cola<Vertice> cola = new Cola<>();
        cola.mete(raiz);

        while(!cola.esVacia()){
            Vertice v = cola.saca();
            accion.actua(v);

            if(v.hayIzquierdo()) cola.mete(v.izquierdo);
            if(v.hayDerecho()) cola.mete(v.derecho);
        }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}