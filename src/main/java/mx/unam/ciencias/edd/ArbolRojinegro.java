package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            super(elemento);
            this.color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        @Override public String toString() {
            return String.format("%s{%s}", color == Color.NEGRO ? "N" : "R", elemento.toString());
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)objeto;
            
            return color == vertice.color && super.equals(objeto);
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() { super(); }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        return ((VerticeRojinegro) vertice).color;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);

        VerticeRojinegro vertice = (VerticeRojinegro) ultimoAgregado;
        vertice.color = Color.ROJO;
        rebalancearAgrega(vertice);
    }

    private void rebalancearAgrega(VerticeRojinegro vertice){
        /* Case 1 */
        if(!vertice.hayPadre()){
            vertice.color = Color.NEGRO;
            return;
        }

        /* Case 2 */
        VerticeRojinegro padre = (VerticeRojinegro) vertice.padre;

        if(esNegro(padre)) return;

        /* Case 3 */
        VerticeRojinegro abuelo = (VerticeRojinegro) padre.padre;
        VerticeRojinegro tio = (VerticeRojinegro) (esDerecho(padre) ? abuelo.izquierdo : abuelo.derecho); 

        if(esRojo(tio)){
            tio.color = padre.color = Color.NEGRO;
            abuelo.color = Color.ROJO;
            rebalancearAgrega(abuelo);
            return;
        }

        /* Case 4 */
        if((esDerecho(padre) && !esDerecho(vertice)) || (!esDerecho(padre) && esDerecho(vertice))){
            if(esDerecho(padre)) super.giraDerecha(padre);
            else super.giraIzquierda(padre);

            VerticeRojinegro aux = vertice;
            vertice = padre;
            padre = aux;
        }

        /* Case 5 */
        padre.color = Color.NEGRO;
        abuelo.color = Color.ROJO;

        if(esDerecho(vertice)) super.giraIzquierda(abuelo);
        else super.giraDerecha(abuelo);
    }

    private boolean esNegro(VerticeRojinegro vertice){
        return vertice == null || vertice.color == Color.NEGRO;
    }

    private boolean esRojo(VerticeRojinegro vertice){
        return vertice != null && vertice.color == Color.ROJO;
    }

    private boolean esDerecho(VerticeRojinegro vertice){
        return vertice.padre.derecho == vertice;
    }

    private void intercambia(VerticeRojinegro a, VerticeRojinegro b){
        VerticeRojinegro aux = b;
        b = a;
        a = aux;
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        VerticeRojinegro vertice = (VerticeRojinegro) super.busca(elemento);
        if(vertice == null) return;

        VerticeRojinegro hijo;
        boolean existF = false;
        elementos--;
        
        if(vertice.hayDerecho() && vertice.hayIzquierdo()) 
            vertice = (VerticeRojinegro) intercambiaEliminable(vertice);

        if(!vertice.hayDerecho() && !vertice.hayIzquierdo()){
            existF = true;
            hijo = (VerticeRojinegro) nuevoVertice(null);
            hijo.color = Color.NEGRO;
            vertice.izquierdo = hijo;
            hijo.padre = vertice;
        }else 
            hijo = (vertice.izquierdo != null) ? (VerticeRojinegro) vertice.izquierdo : (VerticeRojinegro) vertice.derecho;
        
        eliminaVertice(vertice);

        if(esRojo(hijo)){
            hijo.color = Color.NEGRO;
            return;
        }

        if(esRojo(vertice)){
            if(existF) eliminaVertice(hijo);
            return;
        }

        rebalancearELimina(hijo);
        if(existF) eliminaVertice(hijo);
    }

    private void rebalancearELimina(VerticeRojinegro vertice){
        /* Case 1 */
        if(vertice.padre == null){
            return;
        }

        /* Case 2 */
        VerticeRojinegro padre = (VerticeRojinegro) vertice.padre;
        VerticeRojinegro hermano = (vertice == padre.izquierdo) ? (VerticeRojinegro) padre.derecho : (VerticeRojinegro) padre.izquierdo;

        if(esRojo(hermano) && esNegro(padre)){
            padre.color = Color.ROJO;
            hermano.color = Color.NEGRO;

            if(esDerecho(vertice)) super.giraDerecha(padre);
            else super.giraIzquierda(padre); 

            padre = (VerticeRojinegro) vertice.padre;
            hermano = (VerticeRojinegro) (esDerecho(vertice) ? padre.izquierdo : padre.derecho);
        }

        /* Case 3 */
        VerticeRojinegro hIzq = (VerticeRojinegro) hermano.izquierdo;
        VerticeRojinegro hDer = (VerticeRojinegro) hermano.derecho;

        if(esNegro(padre) && esNegro(hermano) && esNegro(hIzq) && esNegro(hDer)){
            hermano.color = Color.ROJO;
            rebalancearELimina(padre);
            return;
        }

        /* Case 4 */
        if(esNegro(hermano) && esNegro(hIzq) && esNegro(hDer) && esRojo(padre)){
            hermano.color = Color.ROJO;
            padre.color = Color.NEGRO;
            return;
        }

        /* Case 5 */
        if(!esDerecho(vertice) && esRojo(hIzq) && esNegro(hDer)){
            hermano.color = Color.ROJO;
            hIzq.color = Color.NEGRO;
            super.giraDerecha(hermano);
            hermano = (VerticeRojinegro) padre.derecho;
        } else if(esDerecho(vertice) && esNegro(hIzq) && esRojo(hDer)){
            hermano.color = Color.ROJO;
            hDer.color = Color.NEGRO;
            super.giraIzquierda(hermano);
            hermano = (VerticeRojinegro) padre.izquierdo;
        }

        hDer = (VerticeRojinegro) hermano.derecho;
        hIzq = (VerticeRojinegro) hermano.izquierdo;

        /* Case 6*/
        hermano.color = padre.color;
        padre.color = Color.NEGRO;

        if(!esDerecho(vertice)){
            hDer.color = Color.NEGRO;
            super.giraIzquierda(padre);
        }else{
            hIzq.color = Color.NEGRO;
            super.giraDerecha(padre);
        } 
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }
}