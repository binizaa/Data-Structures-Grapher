package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void
    quickSort(T[] arreglo, Comparator<T> comparador) {
        auxQuickSort(arreglo, comparador, 0, arreglo.length -1);
    }

    private static <T>void auxQuickSort (T[] A, Comparator<T> comparador, int ini, int fin){
        if (fin <= ini) return;
        int i = ini + 1;
        int j = fin;
        
        while (i < j)
            if(comparador.compare(A[i],A[ini]) > 0 &&
            comparador.compare(A[ini],A[j]) >= 0) intercambia(A,i++,j--);
            else if (comparador.compare(A[ini],A[i]) >= 0) i++;
            else j--;
        
        if(comparador.compare(A[i],A[ini]) > 0) i--;
        intercambia(A,ini,i);
        auxQuickSort(A,comparador, ini, i-1);
        auxQuickSort(A,comparador, i+1,fin);
    }

    private static <T>void intercambia (T[] A, int i, int m){
        T aux = A[i];
        A[i] = A[m];
        A[m] = aux;
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void
    selectionSort(T[] arreglo, Comparator<T> comparador) {
        for(int i = 0; i < arreglo.length; i++){
            int m = i;
            for(int j = i+1; j < arreglo.length; j++)
                if(comparador.compare(arreglo[j],arreglo[m]) < 0) m = j;
            intercambia(arreglo,i,m);
        }
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        int l = 0, r = arreglo.length - 1;

        while(l <= r){
            int m = l + (r-l)/2;
            int comp = comparador.compare(arreglo[m], elemento);

            if(comp == 0) return m;
            else if(comp < 0) l = m + 1;
            else r = m - 1;
        }

        return -1;
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }
}