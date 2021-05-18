/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mastermind;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 *
 * @author Alejandro
 */
public class VistaJuego extends JPanel implements Observer {
    
    ModeloJuego modelo;
    
    private Color colores[];
    
    public VistaJuego(ModeloJuego m) {
        super();
        setBackground(new Color(165,100,32)); // Marrón
        setLayout(new FlowLayout());
    }
    
    public void update(Observable o, Object arg) {
        
    }
}
