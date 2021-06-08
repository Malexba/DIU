package mastermind;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.ArrayList;
import java.util.Observable;
import java.util.StringTokenizer;

/**
 * Modelo del juego.
 * Representación interna del juego del Mastermind.
 * También importa y gestiona los resultados de partidas anteriores.
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
        descifrado = true;
        boolean encontradoC[] = new boolean[4]; // Falsos por defecto; para clave
        boolean encontradoI[] = new boolean[4]; // Falsos por defecto; para intento
        int i, j, k = 0;
        // Búsqueda de coincidencia en posición y color
        for (i = 0; i < 4; i++) {
            if (intento[i] == clave[i]) { // Posición y color; negro
                encontradoC[i] = true;
                encontradoI[i] = true;
                pista[k].setCurrColor(2);
                k++;
            } else { // Ficha no coincide en posición y color, jugador no puede haber ganado
                descifrado = false;
            }  
        }
        // Búsqueda de coincidencia en color, solo buscamos si no ha ganado ya
        if (!descifrado) {
            for (j = 0; j < 4; j++) { // Recorro intento
                if (!encontradoI[j]) {
                    for (i=0; i<4; i++) { // Recorro clave
                        if (!encontradoC[i] && !encontradoI[j]) {
                            if (clave[i] == intento[j]) { // Posición; blanco
                                encontradoI[j] = true;
                                encontradoC[i] = true;
                                pista[k].setCurrColor(1);
                                k++;
                            }
                        }
                    }
                }
            }
        }
        contador--;
        setChanged();
        notifyObservers();
        return descifrado;
    }
    
    public void guardar(int d, String nombre, String tiempo) throws FileNotFoundException { // Guarda resultados de la partida actual
        String nombres[] = new String[10], tiempos[] = new String[10], aux;
        int tam = importar(d,nombres,tiempos);
        boolean incompleto = false;
        if (tam < 10) { // Menos de diez resultados, añadimos uno nuevo; si no, solo sustituimos
            incompleto = true;
            tam++;
        }
        for ( int i=0; i<tam; i++) { // Ordenamos nombres y tiempos
            if ( tiempo.compareTo(tiempos[i]) <= 0 ) { // Resultado actual mejor o igual que el de la posición i-ésima (tiempo menor)
                // Nombres
                aux = nombres[i];
                nombres[i] = nombre;
                nombre = aux;
                // Tiempos
                aux = tiempos[i];
                tiempos[i] = tiempo;
                tiempo = aux;
            }
        }
        if (incompleto) { // Añadimos uno nuevo
            nombres[tam-1] = nombre;
            tiempos[tam-1] = tiempo;
        }
        String archivo; // Guardamos resultados en archivo txt
        if ( d == 4 ) {
            archivo = "Facil.txt";
        } else if ( d == 6) {
            archivo = "Normal.txt";
        } else {
            archivo = "Dificil.txt";
        }
        PrintWriter writer = new PrintWriter(archivo);
        for ( int j = 0; j < tam; j++ ) {
            writer.println(nombres[j]+"/"+tiempos[j]);
        }
        writer.close();
    }
    
    
    
    public int importar(int d, String nombre[], String tiempo[]) { // Importación de resultados
        ArrayList<String> datos;
        if ( d == 4 ) {
            datos = MyInput.leeFichero( "Facil.txt" );
        } else if ( d == 6) {
            datos = MyInput.leeFichero( "Normal.txt" );
        } else {
            datos = MyInput.leeFichero( "Dificil.txt" );
        }
        int tam = datos.size();
        StringTokenizer tokenizer = null;
        for ( int i=0; i<10; i++ ) {
            if (i < tam ) { // Lee líneas del fichero
                tokenizer = new StringTokenizer( datos.get(i), "/" );
                nombre[i] = tokenizer.nextToken();
                tiempo[i] = tokenizer.nextToken();
            } else {
                nombre[i] = "";
                tiempo[i] = "";
            }
        }
        actualizar();
        return tam;
    }
}