package mastermind;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 * Clase que representa a una chincheta de pista.
 * Indica al usuario si ha acertado en su intento solo color (blanco) o también
 * la posición (negro)
 * @author Grupo 5
 */

public class ChinchetaPista extends JComponent {
    
    private int  currColor;

    // constructor
    public ChinchetaPista() {
        currColor = 0;
    }
    
    // Constructor para instrucciones
    public ChinchetaPista(int c) {
        currColor = c;
    }

    // Dibujo de la chincheta
    public void paint(Graphics g) {
        switch (currColor) {
            case 1:
                g.setColor(Color.WHITE);
                g.fillOval(0,0,10,10);
                break;
            case 2:
                g.setColor(Color.BLACK);
                g.fillOval(0,0,10,10);
                break;
            default:
                g.setColor(Color.BLACK);
                g.drawOval(0,0,10,10);
                break;
        }
    }

    // Color actual
    public int getCurrColor() {
        return currColor;
    }

    // Cambiar color actual
    public void setCurrColor(int newColor) {
        currColor = newColor;
    }

    // Dimensiones
    public Dimension getMinimumSize() {
        return new Dimension(10,10);
    }
    public Dimension getPreferredSize() {
        return new Dimension(10,10);
    }
}