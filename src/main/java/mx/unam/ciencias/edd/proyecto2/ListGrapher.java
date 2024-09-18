package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

public class ListGrapher<T> { // Specify generic type T

    GraphicSVG grapher = new GraphicSVG();
    
    private Lista<T> list = new Lista<>();
    private Lines typeLine;

    /**
     * Constructor único.
     */
    public ListGrapher(Lista<T> list, Lines typeLine) {
        this.list = list;
        this.typeLine = typeLine;
    }

    public String graph(){
        StringBuilder str = new StringBuilder(); 
        int x = 10, y = 10;
        int elementos = 1;

        for(T element : list){
            str.append(grapher.rectangle(x, y, element.toString()));
            x += 80;
            if(elementos++ < list.getLongitud()){
                str.append(grapher.line(x+10, y + 20,x + 50, y + 20, typeLine));
                x += 60;
            }
        }

        return grapher.initializeSVG(140 * (list.getElementos() -1) + 100, 60) + str.toString() + grapher.finishSVG(); 
    }

}
