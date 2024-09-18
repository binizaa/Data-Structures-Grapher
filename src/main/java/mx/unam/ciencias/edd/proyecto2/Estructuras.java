package mx.unam.ciencias.edd.proyecto2;

public enum Estructuras {
    LISTA,
    COLA,
    PILA,
    ARBOL_BINARIO_COMPLETO,
    ARBOL_BINARIO_ORDENADO,
    ARBOL_ROJINEGRO,
    ARBOL_AVL,
    GRAFICA,
    MONTICULO_MINIMO,
    INVALIDO;

    public static Estructuras getEstructura(String estructura) {
        switch (estructura.toLowerCase()) {
            case "lista":
                return LISTA;
            case "cola":
                return COLA;
            case "pila":
                return PILA;
            case "arbolbinariocompleto":
                return ARBOL_BINARIO_COMPLETO;
            case "arbolbinarioordenado": 
                return ARBOL_BINARIO_ORDENADO;
            case "arbolavl":
                return ARBOL_AVL;
            case "arbolrojinegro":
                return ARBOL_ROJINEGRO;
            case "grafica":
                return GRAFICA;
            case "monticulominimo":
                return MONTICULO_MINIMO;
            default:
                return INVALIDO;
        }
    }
}
