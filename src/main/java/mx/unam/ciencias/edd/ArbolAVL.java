package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            super(elemento);
            this.altura = 0;
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            return altura;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            return String.format("%s %d/%d", elemento.toString(), altura, balance());
        }

        private int balance() {
            int alturaIzquierdo = (this.izquierdo != null) ? this.izquierdo.altura() : -1;
            int alturaDerecho = (this.derecho != null) ? this.derecho.altura() : -1;
            return alturaIzquierdo - alturaDerecho;
        }
        

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)objeto;
            return (altura == vertice.altura && super.equals(objeto));
        }

        private int alturaSuper() {
            return super.altura();
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() { super(); }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeAVL(elemento);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
        rebalanceo((VerticeAVL) ultimoAgregado.padre);
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a vertice del árbol.
     */
    @Override public void elimina(T elemento) {
        VerticeAVL vertice = (VerticeAVL) super.busca(elemento);

        if (vertice == null) return;
        
        if (vertice.hayIzquierdo() ) 
            vertice = (VerticeAVL) intercambiaEliminable(vertice);

        if (!vertice.hayIzquierdo() && !vertice.hayDerecho())
            eliminaHoja(vertice);
        else eliminaVertice(vertice);
        
        elementos--;
        rebalanceo((VerticeAVL) vertice.padre);
    }

    private void eliminaHoja(Vertice vertice) {
        if (raiz == vertice) {
            raiz = null;
            ultimoAgregado = null;
        } else if (esDerecho(vertice)) vertice.padre.derecho = null;
        else vertice.padre.izquierdo = null;
    }

    private boolean esDerecho(Vertice vertice){
        return vertice.padre.derecho == vertice;
    }

    private void rebalanceo(VerticeAVL vertice){
        if (vertice == null) return;
         
        vertice.altura = vertice.alturaSuper();
        int balance = vertice.balance();
        boolean cambio = false;
        Vertice padre = vertice.padre;
         
        if (balance == -2){
            VerticeAVL q = (VerticeAVL) vertice.derecho;
            VerticeAVL x = (VerticeAVL) q.izquierdo;
            VerticeAVL y = (VerticeAVL) q.derecho;

            if (q.balance() == 1){
                super.giraDerecha(q);
                q.altura = q.alturaSuper();
                x.altura = x.alturaSuper();
            }
            q = (VerticeAVL) vertice.derecho;
            balance = q.balance();

            if (balance == 0 || balance == -1 || balance == -2){
                super.giraIzquierda(vertice);
                vertice.altura = vertice.alturaSuper();
                q.altura = q.alturaSuper();
                cambio = true;
            }

            if (cambio) padre = q.padre;
            else padre = vertice.padre;
            
        } else if (balance == 2){
            VerticeAVL p = (VerticeAVL) vertice.izquierdo;
            VerticeAVL x = (VerticeAVL) p.derecho;
            VerticeAVL y = (VerticeAVL) p.izquierdo;

            if (p.balance() == -1){
                super.giraIzquierda(p);
                p.altura = p.alturaSuper();
                x.altura = x.alturaSuper();
            }

            p = (VerticeAVL) vertice.izquierdo;
            balance = p.balance();

            if (balance == 0 || balance == 1  || balance == 2){
                super.giraDerecha(vertice);
                vertice.altura = vertice.alturaSuper();
                p.altura = p.alturaSuper();
                cambio = true;
            }

            if (cambio) padre = p.padre;
            else padre = vertice.padre;
        }

        rebalanceo((VerticeAVL) padre);
    } 


    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }
}