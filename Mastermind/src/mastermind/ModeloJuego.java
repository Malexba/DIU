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
    private int[] pista; //
    private int[] clave = new int[4];
    private int contador; // Nº de intentos (9 - contador es la fila en el tablero)
    private boolean descifrado;
    
    public ModeloJuego() {
        pista = new int[dificultad];
        generaClave();
    }
    
    // Método para la generación aleatoria y asignación de una clave
    public void generaClave() {
        Random rand = new Random();
        for(int i = 0; i < 4; i++) {
            clave[i] = rand.nextInt(dificultad) + 1;
        }
        descifrado = false;
        contador = 9;
    }
    
    public int intentos() {
        return contador;
    }
    
    public boolean descifrado() {
        return descifrado;
    }
    
    // Devuelve la i-ésima clave
    public int claveI(int i) {
        return clave[i];
    }
    
    public void cambiarDificultad(int d) {
        dificultad = d;
        generaClave();
    }
    
    // Actualización de las chinchetas para repintarlas
    public void actualizar() {
        setChanged();
        notifyObservers();
    }
    
    //devuelve la pista 
    public int  retornaPista(int indice){
         return pista[indice]; 
    }
    //devuelve el tamaño de la pista (número de elementos)(
    public int getTamano(){
        return dificultad;
    }
    
    public boolean compruebaCombinacion(int intento[], ChinchetaPista pista[]) {
        boolean ganador = true;
        boolean encontrado;
        int i, j, k = 0;
        for (i = 0; i < 4; i++) { // Recorro intento
            j = 0;
            encontrado = false;
            do { // Recorro clave
                if ((intento[i] == clave[j]) && (i==j)) { // Posición y color; negro
                    pista[k].setCurrColor(2);
                    k++;
                } else if (intento[i] == clave[j]) { // Posición; blanco
                    pista[k].setCurrColor(1);
                    k++;
                }
                j++;
            } while (!encontrado && (j<4));
        }
        i = 0;
        do {
            if (pista[i].getCurrColor() != 2)
                ganador = false;
            i++;
        } while(ganador && i < 4);
        descifrado = ganador;
        contador--;
        setChanged();
        notifyObservers();
        return ganador;
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