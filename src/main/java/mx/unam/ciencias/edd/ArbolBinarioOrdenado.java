package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Inicializa al iterador. */
        private Iterador() {
            pila = new Pila<>();
            if (esVacia()) return;

            pila.mete(raiz);
            Vertice v = raiz;

            while((v = v.izquierdo) != null) pila.mete(v);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !pila.esVacia();
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override public T next() {
            Vertice v = pila.saca();
            
            if(v.derecho != null){
                Vertice actual = v.derecho;
                while(actual != null){
                    pila.mete(actual);
                    actual = actual.izquierdo;
                }
            }

            return v.elemento;
        }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede
     * garantizar que existe <em>inmediatamente</em> después de haber agregado
     * un elemento al árbol. Si cualquier operación distinta a agregar sobre el
     * árbol se ejecuta después de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        if(elemento == null) throw new IllegalArgumentException("El elemento es nulo");
        Vertice nuevo = nuevoVertice(elemento);
        ultimoAgregado = nuevo;
        elementos++;

        if(esVacia()) raiz = nuevo;
        else auxAgrega(raiz, nuevo);   
    }

    private void auxAgrega(Vertice actual, Vertice nuevo){

        if(nuevo.elemento.compareTo(actual.elemento) > 0){
            if(!actual.hayDerecho()){
                nuevo.padre = actual;
                actual.derecho = nuevo;
            } else auxAgrega(actual.derecho, nuevo);
        }else {
            if(!actual.hayIzquierdo()){
                nuevo.padre = actual;
                actual.izquierdo = nuevo;
            } else auxAgrega(actual.izquierdo, nuevo);
        } 
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Vertice v = vertice(busca(elemento));
        
        elementos--;
        
        if (v.hayIzquierdo() && v.hayDerecho()) { 
            v = intercambiaEliminable(v);
            eliminaVertice(v);
        } else eliminaVertice(v);
    }

    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más
     * un hijo.
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se
     *         intercambió. El vértice regresado tiene a lo más un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        Vertice inter = maximo(vertice.izquierdo);
        
        T aux = inter.elemento;
        inter.elemento = vertice.elemento;
        vertice.elemento = aux;

        return inter;
    }

    private Vertice maximo(Vertice vertice) {
        if (vertice == null) return null;

        while (vertice.hayDerecho()) vertice = vertice.derecho;

        return vertice;
    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de
     * <code>null</code> subiendo ese hijo (si existe).
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo
     *                distinto de <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        Vertice padre = vertice.padre;
        Vertice hijo = vertice.hayIzquierdo() ? vertice.izquierdo : vertice.derecho;
        
        if (padre == null) raiz = hijo;
        else {
            if (padre.derecho== vertice) padre.derecho = hijo;
            else padre.izquierdo = hijo;
        }
        
        if (hijo != null) hijo.padre = padre;
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <code>null</code>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <code>null</code> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
        return auxbusca(raiz,nuevoVertice(elemento));
    }

    private VerticeArbolBinario<T> auxbusca(Vertice actual, Vertice nuevo) {
		if (actual == null) return null;

		if (actual.elemento.compareTo(nuevo.elemento) == 0) return actual;

		if (actual.elemento.compareTo(nuevo.elemento) <= 0)
			return auxbusca(actual.derecho, nuevo);
		return auxbusca(actual.izquierdo, nuevo);
	}

    /**
     * Regresa el vértice que contiene el último elemento agregado al
     * árbol. Este método sólo se puede garantizar que funcione
     * <em>inmediatamente</em> después de haber invocado al método {@link
     * agrega}. Si cualquier operación distinta a agregar sobre el árbol se
     * ejecuta después de haber agregado un elemento, el comportamiento de este
     * método es indefinido.
     * @return el vértice que contiene el último elemento agregado al árbol, si
     *         el método es invocado inmediatamente después de agregar un
     *         elemento al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        return ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        Vertice actual = vertice(vertice);

        if (!actual.hayIzquierdo()) return;

        Vertice hijo = actual.izquierdo;
        hijo.padre = actual.padre;

        if (actual.padre == null) raiz = hijo;
        else {
            if (actual.padre.derecho == actual) actual.padre.derecho = hijo;
            else actual.padre.izquierdo = hijo;
        }

        actual.izquierdo = hijo.derecho;

        if (hijo.hayDerecho()) actual.izquierdo.padre = actual;

        hijo.derecho = actual;
        actual.padre = hijo;
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        Vertice actual = vertice(vertice);

        if (!actual.hayDerecho()) return;

        Vertice hijo = actual.derecho;
        hijo.padre = actual.padre;

        if (actual.padre == null) raiz = hijo;
        else {
            if (actual.padre.izquierdo == actual) actual.padre.izquierdo = hijo;
            else actual.padre.derecho = hijo;
        }

        actual.derecho = hijo.izquierdo;

        if (hijo.hayIzquierdo()) actual.derecho.padre = actual;

        hijo.izquierdo = actual;
        actual.padre = hijo;
    }

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        auxPreOrder(accion, raiz);
    }

    private void auxPreOrder(AccionVerticeArbolBinario<T> accion, Vertice vertice) {
        if (vertice == null) return;
        accion.actua(vertice);
        auxPreOrder(accion, vertice.izquierdo);
        auxPreOrder(accion, vertice.derecho);
    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        auxInOrder(accion, raiz);
    }

    private void auxInOrder(AccionVerticeArbolBinario<T> accion, Vertice vertice) {
        if (vertice == null) return;

        auxInOrder(accion, vertice.izquierdo);
        accion.actua(vertice);
        auxInOrder(accion, vertice.derecho);
    }


    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        auxPostOrder(accion, raiz);
    }

    private void auxPostOrder(AccionVerticeArbolBinario<T> accion, Vertice vertice) {
        if (vertice == null) return;

        auxPostOrder(accion, vertice.izquierdo);
        auxPostOrder(accion, vertice.derecho);
        accion.actua(vertice);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}