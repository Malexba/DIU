package mastermind;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static mastermind.Ventana.w;

/**
 * Ventana con el código de colores de las pistas dadas tras cada intento.
 * @author Grupo 5
 */
public class Pista extends Ventana {
    
    public Pista() {
        add(new JLabel(escalado("../img/Explicacion.png")));
        setTitle("Instrucciones");
        pack();
        setResizable(false);
        setLocation(5*w/8,h/5);
        setVisible(true);
    }
    
    private ImageIcon escalado(String fuente) { // Escalado de imágenes en función del ancho de la consola
        ImageIcon icono = new ImageIcon(getClass().getResource(fuente));
        Image image = icono.getImage(); // Lo transforma
        Image newimg = image.getScaledInstance(w/4, w/12,  java.awt.Image.SCALE_SMOOTH); // Lo escala
        icono = new ImageIcon(newimg);  // Lo destransforma
        return icono;
    }
}
