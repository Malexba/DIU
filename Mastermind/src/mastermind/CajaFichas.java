package mastermind;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JFrame;

/**
 *
 * @author Grupo 5
 */
public class CajaFichas extends JFrame {
    
    private ModeloJuego modelo;
    
    public CajaFichas(ModeloJuego m, Jugar j) {
        modelo = m;
        setLayout(new FlowLayout());
        this.getContentPane().setBackground(new Color(165,100,32)); // Marrón
        for (int i = 1; i<=modelo.getTamano(); i++) {
            add(new Chincheta(modelo, i));
        }
        // Configuracion de la ventana
        setTitle("Fichas");
        pack();
        setResizable(false);
        j.reactivarFichas();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
