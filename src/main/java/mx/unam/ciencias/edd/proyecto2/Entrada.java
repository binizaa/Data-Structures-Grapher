package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream; 

/**
 * La clase Entrada se encarga de gestionar la entrada de datos.
 */
public class Entrada {
    
    /*Argumentos de línea de comandos*/
    private String[] args;

    //private String archivo = "ARCHIVO";

    private String estructura = "ESTRUCTURA";
    
    private Lista<Integer> elementos;

    public Entrada(String[] args){
        this.args = args;
        this.elementos = new Lista<>();
    }

    public String getEstructura(){
        return estructura;
    }

    public Lista<Integer> getElementos(){
        return elementos;
    }

    public void estandar() {
        Lista<String> lista = new Lista<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String linea;
            while ((linea = br.readLine()) != null) lista.agrega(linea);
        } catch (IOException ioe){
            System.out.println("Falló la lectura de la entrada estandar");
            System.out.println("Terminando el programa debido a un error crítico.");
            System.exit(1);
	    }
        procesarEntrada(lista);
    }

    public void entParametros(){
        if(args.length == 1){
            procesarEntrada(abrirArchivo(args[0]));
            return;
        }

        Lista<String> lista = new Lista<>();
        for(int i = 0; i < args.length; i++){
            lista.agrega(args[i]);
        }
        procesarEntrada(lista);
    }

    private void procesarEntrada(Lista<String> lista) {
        Lista<String> nueva = new Lista<>(); 
    
        for (String linea : lista) {
            String[] palabras = linea.trim().split("\\s+"); 
    
            for (String palabra : palabras) {
                if (!palabra.isEmpty()) {
                    if (palabra.startsWith("#")) {
                        break; 
                    }
                    nueva.agrega(palabra);
                }
            }
        }
    
        if (nueva.esVacia()) {
            System.out.println("Error: La lista procesada está vacía.");
            System.out.println("Terminando el programa debido a un error crítico.");
            System.exit(1);
        }
    
        estructura = nueva.eliminaPrimero();
    
        for (String numero : nueva) {
            try {
                int numEntero = Integer.parseInt(numero);
                elementos.agrega(numEntero);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir a entero: " + numero);
                System.out.println("Terminando el programa debido a un error crítico.");
                System.exit(1); 
            }
        }
    }
    

    private Lista<String> abrirArchivo(String archivo){
        Lista<String> lista = new Lista<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archivo)))){		
            String linea = br.readLine();
            while (linea != null){
                lista.agregaFinal(linea);
                linea = br.readLine();
            }
            br.close();
        }catch (IOException ioe){
            System.out.println("No se encuentra el archivo: " + archivo);
            System.out.println("Terminando el programa debido a un error crítico.");
            System.exit(1);
        }
        return lista;
    }
}