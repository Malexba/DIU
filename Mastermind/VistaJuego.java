package mastermind;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 * Tablero de juego del Mastermind.
 * Incluye 10 intentos (junto a las correspondientes pistas) para que el
 * usuario intente acertar el código, también incluido -aunque sin mostrar
 * sus colores- en la parte superior.
 * @author Grupo 5
 */
public class VistaJuego extends JPanel implements Observer {
    
    ModeloJuego modelo;
    
    private Chincheta clave[] = new Chincheta[4];
    private Chincheta combinacion[][] = new Chincheta[10][4];
    private ChinchetaPista pista[][] = new ChinchetaPista[10][4];
    
    public VistaJuego(ModeloJuego m, Chincheta combinacion[][], ChinchetaPista pista[][]) {
        super();
        modelo = m;
        // Creación del tablero
        JPanel tablero = new JPanel();
        tablero.setBackground(new Color(165,100,32));
        tablero.setLayout(new GridLayout(10, 2, 0, 15));
        JPanel panelComb[] = new JPanel[10];
        JPanel panelPista[] = new JPanel[10];
        for (int i=0; i<10; i++) {
            panelPista[i] = new JPanel();
            panelPista[i].setBackground(new Color(165,100,32));
            panelPista[i].setLayout(new GridLayout(2,2));
            panelComb[i] = new JPanel();
            panelComb[i].setBackground(new Color(165,100,32));
            for (int j=0; j<4; j++) {
                pista[i][j] = new ChinchetaPista();
                panelPista[i].add(pista[i][j]);
                combinacion[i][j] = new Chincheta(modelo);
                panelComb[i].add(combinacion[i][j]);
            }
            tablero.add(panelPista[i]);
            tablero.add(panelComb[i]);
        }
        for (int j=0; j<4; j++) {
            combinacion[9][j].setEdit(true);
        }
        this.combinacion = combinacion;
        this.pista = pista;
        setLayout(new BorderLayout());
        setBackground(new Color(165,100,32)); // Marrón
        // Mostramos la clave a adivinar (tapada)
        JPanel codigo = new JPanel();
        codigo.setBackground(new Color(165,100,32));
        codigo.setLayout(new FlowLayout());
        for (int i = 0; i < 4; i++) {
            clave[i] = new Chincheta(modelo, modelo.claveI(i));
            clave[i].setVis(false);
            //System.out.print(clave[i].getCurrColor());
            codigo.add(clave[i]);
        }
        add(codigo,BorderLayout.NORTH);
        // Añadimos el tablero con el que jugará el usuario
        add(tablero,BorderLayout.CENTER);
    }
    
    public void update(Observable o, Object arg) {
        if (modelo.descifrado() || modelo.intentos() < 0) { // Mostramos el código si el usuario ha ganado
            for (int i = 0; i < 4; i++) {
                clave[i].setVis(true);
            }
        }
        if (modelo.reiniciado()) { // Reinicio del juego si se ha perdido
            for (int i = 0; i < 4; i++) {
                clave[i].setColor(modelo.claveI(i));
                clave[i].setVis(false);
                //System.out.print(clave[i].getCurrColor());
            }
            for (int i=0; i<10; i++) {
                for (int j=0; j<4; j++) {
                    pista[i][j].setCurrColor(0);
                    combinacion[i][j].setColor(0);
                    combinacion[i][j].setEdit(false);
                }
            }
            for (int j=0; j<4; j++) {
                combinacion[9][j].setEdit(true);
            }
        }
        repaint();
    }
}
