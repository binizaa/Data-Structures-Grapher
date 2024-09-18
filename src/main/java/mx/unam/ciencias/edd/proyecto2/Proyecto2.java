package mx.unam.ciencias.edd.proyecto2;

/**
 * Proyecto 2.
 */
public class Proyecto2{

    /* Código de terminación por error de uso. */
    private static final int ERROR_USO = 1;

    public static void main(String[] args){
        try {
            Aplicacion aplicacion = new Aplicacion(args);
            aplicacion.ejecuta();
        }catch(IllegalArgumentException iae){
            System.out.println(iae.getMessage());
            System.exit(ERROR_USO);
        }
    }

}