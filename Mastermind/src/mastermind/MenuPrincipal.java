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
 * Gestiona la música de fondo del juego, aparte de permitir el acceso a las
 * diversas opciones de la aplicación.
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
        jugar = new JButton();
        jugar.setIcon(escalado("../img/Jugar.png"));
        MenuPrincipal self = this;
        jugar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Jugar j = new Jugar(modelo, self);
                j.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        rehabilitar();
                    }
                });
                sonidoBoton();
                deshabilitar();
            }
        });
        instrucciones = new JButton();
        instrucciones.setIcon(escalado("../img/Como_jugar.png"));
        instrucciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //new Instrucciones();
                Instrucciones i = new Instrucciones();
                i.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        rehabilitar();
                    }
                });
                sonidoBoton();
                deshabilitar();
            }
        });
        resultados = new JButton();
        resultados.setIcon(escalado("../img/Resultados.png"));
        resultados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //new Resultados();
                sonidoBoton();
                deshabilitar();
            }
        });
        opciones = new JButton();
        opciones.setIcon(escalado("../img/Opciones.png"));
        opciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Opciones opc = new Opciones(modelo, self);
                opc.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        rehabilitar();
                    }
                });
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
    
    private ImageIcon escalado(String fuente) { // Escalado de imágenes en función del ancho de la consola
        ImageIcon icono = new ImageIcon(getClass().getResource(fuente));
        Image image = icono.getImage(); // Lo transforma
        Image newimg = image.getScaledInstance(w/12, w/12,  java.awt.Image.SCALE_SMOOTH); // Lo escala
        icono = new ImageIcon(newimg);  // Lo destransforma
        return icono;
    }
    
    public void deshabilitar() { // Desactivar los botones del menú
        jugar.setEnabled(false);
        instrucciones.setEnabled(false);
        resultados.setEnabled(false);
        opciones.setEnabled(false);
    }
    
    public void rehabilitar() { // Reactivar los botones del menú
        jugar.setEnabled(true);
        instrucciones.setEnabled(true);
        resultados.setEnabled(true);
        opciones.setEnabled(true);
    }
    
    public void sonidoContinuo(){ // Carga y reproducción de la música de fondo
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

    public void pararSonido(){ // Para la música de fondo
        sc.stop();
        sonando = false;
    }

    public void iniciarSonidoContinuo(){ // Reproducción de la música de fondo
        sc.loop(Clip.LOOP_CONTINUOUSLY);
        sc.start();
        sonando = true;
    }
    
    public boolean sonando() { // Indica si la pista se está reproduciendo
        return sonando;
    }
}
