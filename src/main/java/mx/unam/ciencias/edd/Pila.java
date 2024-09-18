package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la pila.
     * @return una representación en cadena de la pila.
     */
    @Override public String toString() {
        String aux = "";
        Nodo nodo = cabeza;

        while(nodo != null){
            aux += nodo.elemento.toString() + "\n";
            nodo = nodo.siguiente;
        }

        return aux;
    }

    /**
     * Agrega un elemento al tope de la pila.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
        if(elemento == null) throw new IllegalArgumentException("El elemento es nulo");
        Nodo nuevo = new Nodo(elemento);
        
        if(esVacia()) rabo = cabeza = nuevo;
        else{
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
        }
    }
}
