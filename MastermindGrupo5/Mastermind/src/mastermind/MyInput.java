package mastermind;

import java.io.*;
import java.util.*;

/**
 * Clase que contiene los métodos fundamentales para leer datos de un fichero.
 * @author Grupo 5
 */
public	class	MyInput {
    /**
     * Lee una cadena de caracteres desde el teclado
     * @return Cadena de caracteres leída
     */
    public static String readString() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in),1);
        String string="";
        try {
            string = br.readLine();	
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return string;
    }
    
    /**
     * Lee un dato tipo int desde el teclado. En caso de que el usuario
     * aporte un dato de tipo no entero, se muestra error y vuelve a pedir
     * el dato al usuario.
     * @return Entero aportado por el usuario
     */
    public static int readInt()	{
        boolean log = true;
        int i = 0;
        do{
            try{
                i = Integer.parseInt( readString() );
                log = false;            
            } catch( NumberFormatException nfe ) {
                System.out.println( "El formato del numero no es correcto." );
                System.out.println( "Introduce de nuevo el numero: " );
            }
        } while ( log );
        return i;
    }
    
    /**
     * Lee un dato tipo double desde el teclado
     * @return Dato leído
     */
    public static double readDouble()	{
        return	Double.parseDouble(readString());	
    }
    
    /**
     * Lee un dato tipo byte desde el teclado
     * @return Byte que se lee
     */
    public static byte readByte()	{
        return	Byte.parseByte(readString());	
    }
    
    /**
     * Lee un dato tipo short desde el teclado
     * @return Dato aportado por el usuario
     */
    public static short	readShort()	{
        return	Short.parseShort(readString());	
    }
    
    /**
     * Lee un dato tipo long desde el teclado
     * @return Dato leído
     */
    public static long readLong()	{
        return	Long.parseLong(readString());	
    }
    
    /**
     * Lee un dato tipo float desde el teclado. En caso de que el usuario
     * aporte un dato de tipo no float, se muestra error y vuelve a pedirlo.
     * @return Float que se lee
     */
    public static float readFloat()	{
        boolean log = true;
        float i = 0;
        do{
            try{
                i = Float.parseFloat( readString() );
                log = false;            
            } catch( NumberFormatException nfe ) {
                System.out.println( "El formato del numero no es correcto." );
                System.out.println( "Introduce de nuevo el numero: " );
            }
        } while ( log );
        return i;
    }
    
    /**
     * Valida la nota aportada por el usuario al calificar una asignatura. En
     * caso de que el formato no sea correcto, se vuelve a pedir la nota al
     * usuario.
     * @param num String con la nota aportada por el usuario
     * @return Nota de la asignatura
     */
    public static float readFloat( String num ) {
        boolean log = true;
        float i = 0;
        do{
            try{
                i = Float.parseFloat( num );
                log = false;            
            } catch( NumberFormatException nfe ) {
                if ( num.equals("NP") ) {
                    log = false;
                } else {
                    System.out.println( "El formato de la nota no es correcta." );
                    System.out.println( "Introduce de nuevo la nota: " );
                    num = MyInput.readString();
                }
            }
        } while ( log );
        return i;
    }
    
    /**
     * Lee un ArrayList desde un fichero
     * @param nombreFichero Nombre del fichero a leer
     * @return Arraylist con el contenido del fichero
     */
    public static ArrayList <String> leeFichero(String nombreFichero){
        ArrayList <String> v = new ArrayList <String>();
        File fichero=null;
        FileReader fr=null;
        BufferedReader br=null;
        try{
            fichero=new File(nombreFichero);
            fr=new FileReader(fichero);
            br=new BufferedReader(fr);
            String linea;
            while ((linea=br.readLine())!=null){
                v.add(linea);}
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (null!= fr){
                    fr.close();
                    br.close();
                }
            } catch (Exception e1){
                e1.printStackTrace();
            }
        }
        return v;
    }
}
