package mastermind;

import java.util.Random;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Modelo del juego.
 * Representación interna del juego del Mastermind.
 * @author Grupo 5
 */
public class ModeloJuego extends Observable {
    
    private int dificultad = 6; // 4 fácil, 6 normal, 8 difícil (indica nº de bolas)
    private int[] pista;
    private int[] clave;
    
    public ModeloJuego() {
        pista = new int[dificultad];
        generaClave();
    }
    
    // Método para la generación aleatoria y asignación de una clave
    public void generaClave() {
        Random rand = new Random();
        int[] temp = new int[dificultad];
        for(int i = 0; i < dificultad; i++) {
            temp[i] = rand.nextInt(6);
        }
        clave = temp;
    }
    
    public void cambiarDificultad(int d) {
        dificultad = d;
        generaClave();
    }
    
    //devuelve la pista 
    public int  retornaPista(int indice){
         return pista[indice]; 
    }
    //devuelve el tamaño de la pista (número de elementos)(
    public int getTamano(){
        return dificultad;
    }
    /**
     * 
     * @param combinacion representa la combinación, en forma de String, que introduce el usuario. El formato es una serie de  N números o caracteres que representan la combinación, donde N es el número de elementos de la combinación.
     * @param clave representa la clave, en forma de array de enteros, que se debe acertar. El formato es una serie de  N números o caracteres que representan la clave, donde N es el número de elementos de la clave.
     * @return true si el usuario acierta la clave a través de su combinación y false en caso contrario.
     */ 
    public boolean compruebaCombinacion(String combinacion, String clave){
        // inicia los valores del array pista a cero
        for(int i=0; i<pista.length;i++)
              pista[i]=0;
        
        ArrayList<Character> combinacionA=new ArrayList<Character>(); 
        ArrayList<Character> claveA=new ArrayList<Character>(); 
        int contador=0;
        
        //registro con un 2 en la pista aquellos elementos de la combinación y la clave que coinciden en posición y valor.
        for(int i=0; i<combinacion.length();i++)
            if (clave.charAt(i)==combinacion.charAt(i)){
                pista[contador]=2;
                contador+=1;
            } else{
                combinacionA.add(combinacion.charAt(i));
                claveA.add(clave.charAt(i));
            }
        //registro con un 1 en la pista aquellos elementos de la combinación y la clave que solo conciden en valor.
        for(int i=0; i<combinacionA.size();i++)
                for(int j=0; j<claveA.size();j++)
                    if(combinacionA.get(i).equals(claveA.get(j))){
                        claveA.remove(j);
                        pista[contador]=1; 
                        contador+=1;
                        break;
                    }
        
        //los elementos que no coniciden de ninguna forma permanecen a cero en la pista. 
        setChanged();
        notifyObservers();
       
        boolean ganador=true;
        // Se comprueba si se ha acertado la combinación chequeando que todas las posiciones contengan un 2.
        for(int k=0; k<pista.length;k++)
            ganador=ganador&&(pista[k]==2);
        
        if(ganador)
            return true;
        else
            return false;    
    }
}