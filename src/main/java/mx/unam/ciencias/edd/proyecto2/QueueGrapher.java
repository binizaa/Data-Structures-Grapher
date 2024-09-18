package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

public class QueueGrapher<T> extends ListGrapher<T> {

    public QueueGrapher(Lista<T> list) {
        super(list, Lines.RIGHT);
    }
}
