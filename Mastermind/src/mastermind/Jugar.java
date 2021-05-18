package mastermind;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Clase que representa el controlador del juego.
 * @author Grupo 5
 */
public class Jugar extends Ventana {
    
    private ModeloJuego modelo;
    private VistaJuego tablero = new VistaJuego(modelo);
    
    private JButton fichas, instrucciones, reloj, borrar, comprobar;
    
    public Jugar(ModeloJuego model, MenuPrincipal m) {
        modelo = model;
        Jugar self = this;
        setLayout(new BorderLayout());
        // Configuro botones de la parte superior
        JPanel btnPanelSup = new JPanel(new FlowLayout());
        fichas = new JButton("Fichas");
        fichas.setEnabled(false);
        fichas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new CajaFichas(modelo,self);
                fichas.setEnabled(false);
                sonidoBoton();
            }
        });
        btnPanelSup.add(fichas);
        instrucciones = new JButton("Instrucciones");
        instrucciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //new Pista();
                sonidoBoton();
            }
        });
        btnPanelSup.add(instrucciones);
        reloj = new JButton("Reloj");
        reloj.setEnabled(false);
        reloj.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //Volver a mostrar reloj
                reloj.setEnabled(false);
                sonidoBoton();
            }
        });
        btnPanelSup.add(reloj);
        add(btnPanelSup,BorderLayout.NORTH);
        // Muestro el juego por pantalla y asocio vista con modelo
        modelo.addObserver(tablero);
        add(tablero,BorderLayout.CENTER);
        // Configuro botones de la parte inferior
        JPanel btnPanelInf = new JPanel(new FlowLayout());
        borrar = new JButton("Borrar");
        borrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                // Función de borrado del modelo
                sonidoBoton();
            }
        });
        btnPanelInf.add(borrar);
        comprobar = new JButton("¡Lo tengo!");
        comprobar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                // Función de comprobación del modelo
                sonidoBoton();
            }
        });
        btnPanelInf.add(comprobar);
        add(btnPanelInf,BorderLayout.SOUTH);
        // Configuracion de la ventana
        //new VistaReloj();
        new CajaFichas(modelo,self);
        setTitle("Jugar");
        pack();
        setResizable(false);
        reactivar(m);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void reactivarFichas() {
        fichas.setEnabled(true);
    }
    
    public void reactivarReloj() {
        reloj.setEnabled(true);
    }
}
