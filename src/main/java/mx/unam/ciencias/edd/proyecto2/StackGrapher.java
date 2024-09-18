package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

public class StackGrapher<T> { // Specify generic type T

    GraphicSVG grapher = new GraphicSVG();
    
    private Lista<T> list = new Lista<>();

    /**
     * Constructor único.
     */
    public StackGrapher(Lista<T> list) {
        this.list = list;
    }

    public String graph(){
        StringBuilder str = new StringBuilder(); // Initialize a StringBuilder
        int x = 10, y = 10;
        int elementos = 1;
        
        for(T element : list){
            str.append(grapher.rectangle(x, y, element.toString()));
            y += 40;
        }

        return grapher.initializeSVG(100, 40 * list.getElementos() + 20) + str.toString() + grapher.finishSVG(); 
    }

}
