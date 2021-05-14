package mastermind;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.swing.*;

/**
 * Clase que representa al menú principal.
 * @author Grupo 5
 */
public class MenuPrincipal extends Ventana {
    
    private ModeloJuego modelo = new ModeloJuego();
    
    private JButton jugar, instrucciones, resultados, opciones;
    private Clip sc;
    private boolean sonando;
    
    public MenuPrincipal() {
        sonidoContinuo();
        sonando = true;
        // Creamos los botones y les asignamos acciones
        jugar = new JButton("Jugar");
        MenuPrincipal self = this;
        jugar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new Jugar(modelo, self);
                sonidoBoton();
                deshabilitar();
            }
        });
        instrucciones = new JButton("¿Cómo se juega?");
        instrucciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //new Instrucciones();
                sonidoBoton();
                deshabilitar();
            }
        });
        resultados = new JButton("Resultados");
        resultados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //new Resultados();
                sonidoBoton();
                deshabilitar();
            }
        });
        opciones = new JButton("Opciones");
        opciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new Opciones(modelo, self);
                sonidoBoton();
                deshabilitar();
            }
        });
        // Añadimos los botones a la ventana
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(jugar);
        add(instrucciones);
        add(resultados);
        add(opciones);
        // Configuracion de la ventana
        setTitle("Menú principal");
        pack();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void deshabilitar() {
        jugar.setEnabled(false);
        instrucciones.setEnabled(false);
        resultados.setEnabled(false);
        opciones.setEnabled(false);
    }
    
    public void rehabilitar() {
        jugar.setEnabled(true);
        instrucciones.setEnabled(true);
        resultados.setEnabled(true);
        opciones.setEnabled(true);
    }
    
    public void sonidoContinuo(){
        sc = null;
        try{
            URL soundURL = getClass().getClassLoader().getResource("sounds/bensound-enigmatic.wav");
            Line.Info linfo = new Line.Info(Clip.class);
            Line line = AudioSystem.getLine(linfo);
            sc = (Clip) line;
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL);
            sc.open(ais);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        sc.loop(Clip.LOOP_CONTINUOUSLY);
        sc.start();
    }

    public void pararSonido(){
        sc.stop();
        sonando = false;
    }

    public void iniciarSonidoContinuo(){
        sc.loop(Clip.LOOP_CONTINUOUSLY);
        sc.start();
        sonando = true;
    }
    
    public boolean sonando() {
        return sonando;
    }
}
