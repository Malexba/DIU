package mastermind;

import java.util.Observable;

/**
 * Modelo del juego.
 * Representación interna del juego del Mastermind.
 * @author Grupo 5
 */
public class ModeloJuego extends Observable {
    
    private int dificultad = 1; // 0 fácil, 1 normal, 2 difícil
    
    public ModeloJuego() {
        
    }
    
    public void cambiarDificultad(int d) {
        dificultad = d;
    }
}