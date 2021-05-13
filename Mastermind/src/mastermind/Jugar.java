package mastermind;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Clase que representa el controlador del juego.
 * @author Grupo 5
 */
public class Jugar extends Ventana {
    
    private ModeloJuego modelo;
    private VistaJuego tablero = new VistaJuego(modelo);
    
    public Jugar(ModeloJuego model) {
        modelo = model;
        //new Reloj();
        //new CajaFichas();
        setLayout(new BorderLayout());
        // Configuro botones de la parte superior
        JPanel btnPanelSup = new JPanel(new FlowLayout());
        JButton fichas = new JButton("Fichas");
        fichas.setEnabled(false);
        fichas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //new Jugar();
                //sonidoBoton();
            }
        });
        JButton instrucciones = new JButton("Instrucciones");
        instrucciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //new Pista();
                //sonidoBoton();
            }
        });
        JButton reloj = new JButton("Reloj");
        reloj.setEnabled(false);
        reloj.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //new Reloj();
                //sonidoBoton();
            }
        });
        add(btnPanelSup,BorderLayout.NORTH);
        // Muestro el juego por pantalla y ascoio vista con modelo
        modelo.addObserver(tablero);
        add(tablero,BorderLayout.CENTER);
        // Configuro botones de la parte inferior
        JPanel btnPanelInf = new JPanel(new FlowLayout());
        JButton borrar = new JButton("Borrar");
        borrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                // Función de borrado del modelo
                //sonidoBoton();
            }
        });
        JButton comprobar = new JButton("¡Lo tengo!");
        comprobar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                // Función de comprobación del modelo
                //sonidoBoton();
            }
        });
        add(btnPanelInf,BorderLayout.SOUTH);
        // Configuracion de la ventana
        setTitle("Jugar");
        pack();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
