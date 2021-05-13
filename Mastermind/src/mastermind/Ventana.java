package mastermind;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.swing.JFrame;

/**
 * Clase general de una ventana.
 * Instancia los métodos de gestión de sonido; tanto el continuo que suena a lo
 * largo del juego, como los de selección de botones.
 * @author Grupo 5
 */
public class Ventana extends JFrame {
    
    public void sonidoBoton() {
        Clip c = null;
        try{
            URL soundURL = getClass().getClassLoader().getResource("sounds/button.wav");
            Line.Info linfo = new Line.Info(Clip.class);
            Line line = AudioSystem.getLine(linfo);
            c = (Clip) line;
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL);
            c.open(ais);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        c.setMicrosecondPosition(0);
        c.start();
    }
    
    public void reactivar(MenuPrincipal m) { // Función para reactivar los comandos del menú principal
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                m.rehabilitar();
            }
        });
    }
}
