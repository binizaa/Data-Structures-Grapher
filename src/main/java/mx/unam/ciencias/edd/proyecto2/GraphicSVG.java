package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.proyecto2.Lines;

public class GraphicSVG {

    public String initializeSVG (int x, int y){
        return String.format("<?xml version='1.0' encoding='UTF-8' ?><svg width='%d' height='%d'><g>", x, y);
    }

    public String rectangle (int x, int y, String text) {
        String rect = String.format("<rect width='80' height='40' x=\"%d\" y=\"%d\" stroke='black' fill='white' />", x, y);
        rect += text(40 + x, 29 + y, text, "black", 25);
        return rect;
    }

    public String line(int x1, int y1, int x2, int y2, Lines type){
        String line = String.format("<path d=\"M%d,%d L%d,%d\" stroke=\"black\" stroke-width=\"3\"/>", x1, y1, x2, y2);
        String startArrow = String.format("<polygon points=\"%d,%d %d,%d %d,%d\" fill=\"black\"/>", x1 - 5, y1, x1, y1 - 5, x1, y1 + 5);
        String endArrow = String.format("<polygon points=\"%d,%d %d,%d %d,%d\" fill=\"black\"/>", x2, y2 - 5, x2, y2 + 5, x2 + 5, y2);

        if(type == Lines.NEITHER) return line;
        if(type == Lines.BOTH) return line + startArrow + endArrow;
        if(type == Lines.RIGHT) return line + endArrow;
        return line + endArrow + startArrow;
    }

    public String circle(int cx, int cy, String color, String text, int r) {
        String colorLetter = color == "white" ? "black" : "white";
        String circleSVG = String.format("<circle cx='%d' cy='%d' r='%d' stroke='black' stroke-width='3' fill='%s' />", cx, cy,r, color);
        return circleSVG + text(cx, cy + 8, text, colorLetter, 32);
    }
    

    public String text(int x, int y, String text, String color, int tamanoFuente) {
        return String.format("<text x='%d' y='%d' text-anchor='middle'" +
                " font-family='sans-serif' font-size='%d' fill='%s'>%s</text>",
                x, y, tamanoFuente, color, text);
    }

    public String finishSVG () {
        return "</g></svg>";
    }

    public static String curva(int x1, int y1, int x2, int y2, int x3, int y3) {
        return String.format("<path d='M %d %d q %d %d %d %d' stroke='black'"+ " stroke-width='3' fill='none' />", x1, y1, x3, y3, x2, y2);
    }
    
}