package mx.unam.ciencias.edd;

/**
 * Clase para valores indexables.
 */
public class ValorIndexable<T>
    implements ComparableIndexable<ValorIndexable<T>> {

    /* El elemento */
    private T elemento;
    /* Su valor */
    private double valor;
    /* Su índice. */
    private int indice;

    /**
     * Crea un nuevo valor indexable con el elemento y valor dados.
     * @param elemento el elemento.
     * @param valor su valor.
     */
    public ValorIndexable(T elemento, double valor) {
        this.elemento = elemento;
        this.valor = valor;
        this.indice = -1;
    }

    /**
     * Regresa el elemento del valor indexable.
     * @return el elemento del valor indexable.
     */
    public T getElemento() {
        return elemento;
    }

    /**
     * Compara el valor indexable con otro valor indexable.
     * @param valorIndexable el valor indexable.
     * @return un valor menor que cero si el valor indexable que llama el método
     *         es menor que el parámetro; cero si son iguales; o mayor que cero
     *         si es mayor.
     */
    @Override public int compareTo(ValorIndexable<T> valorIndexable) {
        if(this.valor == valorIndexable.valor) return 0;
        if(this.valor < valorIndexable.valor) return -1;
        return 1;
    }

    /**
     * Define el índice del valor indexable.
     * @param indice el nuevo índice.
     */
    @Override public void setIndice(int indice) {
        this.indice = indice;
    }

    /**
     * Regresa el índice del valor indexable.
     * @return el índice del valor indexable.
     */
    @Override public int getIndice() {
        return indice;
    }

    /**
     * Define el valor del valor indexable.
     * @param valor el nuevo valor.
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * Regresa el valor del valor indexable.
     * @return el valor del valor indexable.
     */
    public double getValor() {
        return valor;
    }

    /**
     * Nos dice si el valor indexable es igual al objeto recibido.
     * @param objeto el objeto con el que queremos comparar el valor indexable.
     * @return <code>true</code> si el objeto recibido es un valor indexable
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") ValorIndexable<T> valorIndexable =
            (ValorIndexable<T>)objeto;
        return this.valor == valorIndexable.valor && this.indice == valorIndexable.indice && this.elemento == valorIndexable.elemento;
    }

    /**
     * Regresa una representación en cadena del valor indexable.
     * @return una representación en cadena del valor indexable.
     */
    @Override public String toString() {
        return String.format("%s:%2.9f", elemento, valor);
    }
}
