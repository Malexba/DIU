package mastermind;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;

/**
 * Ventana para visualizar el tiempo de juego actual.
 * Vista del modelo de reloj. Muestra horas, minutos y segundos que han
 * pasado desde que se inició la partida actual.
 * @author Grupo 5
 */
public class VistaReloj extends Ventana implements Observer {
    
    ModeloReloj modelo;
    
    private JLabel tiempo;
    
    public VistaReloj(ModeloReloj m) {
        modelo = m;
        setLayout(new BorderLayout());
        tiempo = new JLabel(String.format("%02d", modelo.horas()) + " : " + String.format("%02d", modelo.minutos()) + " : " + String.format("%02d", modelo.segundos()), JLabel.CENTER);
        tiempo.setAlignmentX(Component.CENTER_ALIGNMENT);
        tiempo.setFont(new Font("Arial", Font.PLAIN, Math.round(w/30))); // Definimos el formato del texto de la etiqueta
        add(tiempo, BorderLayout.CENTER);
        // Configuracion de la ventana
        setTitle("Reloj");
        pack();
        setResizable(false);
        setLocation(w/8,h/5);
        setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        tiempo.setText(String.format("%02d", modelo.horas()) + " : " + String.format("%02d", modelo.minutos()) + " : " + String.format("%02d", modelo.segundos()));
    }
}
