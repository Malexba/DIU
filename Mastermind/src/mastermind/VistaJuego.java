/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mastermind;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 *
 * @author Alejandro
 */
public class VistaJuego extends JPanel implements Observer {
    
    ModeloJuego modelo;
    
    
    
    public VistaJuego(ModeloJuego m) {
        super();
    }
    
    public void update(Observable o, Object arg) {
        
    }
}
