package mastermind;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.swing.JFrame;

/**
 * Clase general de una ventana.
 * Instancia los métodos de gestión de sonido; tanto el continuo que suena a lo
 * largo del juego, como los de selección de botones. También incluye los
 * relativos a adaptar elementos a la resolución de la pantalla.
 * @author Grupo 5
 */
public class Ventana extends JFrame {
    
    public final static int w = escalado(true);
    public final static int h = escalado(false);
    
    public void sonidoBoton() { // Sonido reproducido al pulsar un botón
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
    
    public void cerrar() { // Función para cerrar esta ventana
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
    
    // Función para obtener ancho y alto de la consola. Devuelve ancho o alto en función del booleano
    private static int escalado(Boolean b) {
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension sPantalla = t.getScreenSize();
        if (b) { // Ancho
            int wPantalla = (int)((sPantalla.getWidth()));
            return wPantalla;
        } else { // Alto
            int hPantalla = (int)((sPantalla.getHeight()));
            return hPantalla;
        }
    }
}
