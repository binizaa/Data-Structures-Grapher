package mx.unam.ciencias.edd;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
 
/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        private T elemento;
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nodo con un elemento. */
        private Nodo(T elemento) {
            this.elemento = elemento;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nuevo iterador. */
        private Iterador() {
            siguiente = cabeza;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            if(!hasNext()) throw new NoSuchElementException ("No hay elemento siguiente");
            anterior = siguiente;
            siguiente = siguiente.siguiente;
            return anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            if(!hasPrevious()) throw new NoSuchElementException ("No hay elemento anterior");
            else {
                siguiente = anterior;
                anterior = anterior.anterior;
                return siguiente.elemento;
            }
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            siguiente = cabeza;
	        anterior = null;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            anterior = rabo;
	        siguiente = null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return rabo == null;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        agregaFinal(elemento);
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        if(elemento == null) throw new IllegalArgumentException("El elemento es nulo");
        Nodo nodo = new Nodo(elemento);
        if(esVacia()) rabo = cabeza = nodo;
        else{
            nodo.anterior = rabo;
            rabo.siguiente = rabo = nodo;
        }
        longitud++;
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        if(elemento==null) throw new IllegalArgumentException("El elemento es nulo");
        Nodo nodo = new Nodo(elemento);
        if(esVacia()){
            rabo = cabeza = nodo;
            nodo.siguiente = null;
        }else{
            nodo.siguiente = cabeza;
            cabeza.anterior = cabeza = nodo;
        }
        longitud++;
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        if(elemento == null) throw new IllegalArgumentException("El elemento es nulo");
        if(i<=0) agregaInicio(elemento);
        else if(i >= longitud) agregaFinal(elemento);
        else{
            Nodo sig = buscaIndice(i);
            if(sig == null) return;
            Nodo nuevo = new Nodo (elemento);
            Nodo ant = sig.anterior;

            nuevo.anterior = ant;
            ant.siguiente = sig.anterior = nuevo;
            nuevo.siguiente = sig;
            longitud++;
        } 
    }

    private Nodo buscaIndice(int indice){
        Nodo nodo = cabeza;
        int i = 0;
        while(nodo != null){
            if(indice == i) return nodo;
            i++;
            nodo = nodo.siguiente;
        }
        return null;
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Nodo nodo = buscarNodo(elemento);

        if(nodo == null) return;
        else if(rabo == cabeza && cabeza == nodo) limpia();
        else if(nodo == cabeza) eliminaPrimero();
        else if(nodo == rabo) eliminaUltimo();
        else{
            nodo.anterior.siguiente = nodo.siguiente;
            nodo.siguiente.anterior = nodo.anterior;
            longitud--;
        }
    }

    private Nodo buscarNodo(Object elemento){
        Nodo nodo = cabeza;
        while(nodo != null){
            if(nodo.elemento.equals(elemento)) return nodo;
            nodo = nodo.siguiente;
        }
        return null;
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        if(esVacia()) throw new NoSuchElementException("La lista es vacía");
        Nodo eliminado = cabeza;
        if(cabeza == rabo) limpia();
        else {
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
            longitud--;
        }
        return eliminado.elemento;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        if(esVacia()) throw new NoSuchElementException("La lista esta vacía");
        Nodo eliminado = rabo;
        if(cabeza == rabo) limpia();
        else {
            rabo = rabo.anterior;
            rabo.siguiente = null;
            longitud--;
        }
        return eliminado.elemento;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        return buscarNodo(elemento)!=null;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Lista<T> listaR = new Lista<>();
        for(T elemento : this) listaR.agregaInicio(elemento);
        return listaR;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        Lista<T> listaC = new Lista<>();
        for(T elemento : this) listaC.agregaFinal(elemento);
        return listaC;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        cabeza = rabo = null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        if (esVacia()) throw new NoSuchElementException("La lista está vacía");
        return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        if (esVacia()) throw new NoSuchElementException("La lista está vacía");
        return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        if(i<0 || i>=longitud) throw new ExcepcionIndiceInvalido("El indice es inválido");
        Nodo nodo = cabeza;
        int c = 0;
        while(c++<i) nodo = nodo.siguiente;
        return nodo.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        int i = 0;
        Nodo nodo = cabeza;
        while(nodo != null){
            if(nodo.elemento.equals(elemento)) return i;
            i++;
            nodo = nodo.siguiente;
        }
        return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        if(esVacia()) return "[]";

        String cadena = "[";
        Nodo nodo = cabeza;
        for(int i = 0;i<longitud-1;i++){
            cadena += String.format("%s, ",nodo.elemento);
            nodo = nodo.siguiente;
        }
        cadena += String.format("%s]", rabo.elemento); 

        return cadena;
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        
        if (longitud != lista.getLongitud()) return false;
        Nodo nodo = cabeza;
        Nodo auxN = lista.cabeza;
        
        while(nodo != null){
            if (!nodo.elemento.equals(auxN.elemento)) return false;
            nodo = nodo.siguiente;
            auxN = auxN.siguiente;
        }
        return true;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        return auxMerge(copia(), comparador);
    }

    private Lista<T> auxMerge(Lista<T> lista, Comparator<T> comparador){
        if(lista.getLongitud() < 2)  return lista;

        int mitad = lista.getLongitud()  / 2;
        Lista<T> izquierda = new Lista<T>();
        Lista<T> derecha = lista;

        for (int i = 0; i < mitad; i++) izquierda.agregaFinal(derecha.eliminaPrimero());

        izquierda = auxMerge(izquierda, comparador);
        derecha = auxMerge(derecha, comparador);
        return mezcla(izquierda, derecha,comparador);
    }

    private Lista<T> mezcla (Lista<T> izq, Lista<T> der, Comparator<T> comparador) {
        Lista<T> mezcla = new Lista<T>();

        while (!izq.esVacia() && !der.esVacia())
            if (comparador.compare(izq.cabeza.elemento, der.cabeza.elemento) <= 0) 
                mezcla.agregaFinal(izq.eliminaPrimero()); 
            else mezcla.agregaFinal(der.eliminaPrimero());
            
        while (!izq.esVacia()) mezcla.agregaFinal(izq.eliminaPrimero());
    
        while (!der.esVacia()) mezcla.agregaFinal(der.eliminaPrimero());
    
        return mezcla;
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
    Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        for(T nodo : this)
            if(comparador.compare(nodo, elemento) == 0) return true;
            else if(comparador.compare(elemento, nodo) < 0) return false;

        return false;
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
}
