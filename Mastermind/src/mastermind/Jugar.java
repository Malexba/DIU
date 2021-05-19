package mastermind;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 * Clase que representa el controlador del juego.
 * @author Grupo 5
 */
public class Jugar extends Ventana {
    
    private ModeloJuego modelo;
    private VistaJuego tablero;
    
    private int ans; // Respuesta a OptionPane
    private boolean intentoValido; // Comprueba que todos los círculos estén rellenos
    private int intento[];
    private JButton fichas, instrucciones, reloj, borrar, comprobar;
    private Chincheta combinacion[][];
    private ChinchetaPista pista[][];
    
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
        // Creamos el tablero, muestro el juego por pantalla y asocio vista con modelo
        tablero = new VistaJuego(modelo, combinacion, pista);
        modelo.addObserver(tablero);
        add(tablero,BorderLayout.CENTER);
        // Configuro botones de la parte inferior
        JPanel btnPanelInf = new JPanel(new FlowLayout());
        borrar = new JButton("Borrar");
        borrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                System.out.println(modelo.intentos());
                for (int j=0; j<4; j++){
                    combinacion[modelo.intentos()][j].resetearChincheta();
                }
                sonidoBoton();
            }
        });
        btnPanelInf.add(borrar);
        comprobar = new JButton("¡Lo tengo!");
        comprobar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                intentoValido = true;
                for (int j=0; j<4; j++) {
                    intento[j] = combinacion[modelo.intentos()][j].getCurrColor();
                    if (intento[j] == 0) {
                        intentoValido = false;
                    }
                }
                if (intentoValido) {
                    if (modelo.compruebaCombinacion(intento, pista[modelo.intentos()])) { // Jugador ha ganado la partida, guardamos su nombre
                        String inputValue = JOptionPane.showInputDialog("¡Felicidades, lo has descifrado!\nEscribe aquí el nombre por el\nque quieres que se te recuerde:");
                    } else { // Jugador ha perdido un intento, comprobamos que no haya perdido la partida
                        if (modelo.intentos() < 0) { // Jugador se ha quedado sin intentos
                            ans = JOptionPane.showConfirmDialog(null,"¡Casi lo tenías!\n¿Quieres jugar otra vez?", "Derrota", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                            if ( ans == 0 ) { // Reiniciamos código para adivinar
                                modelo.cambiarDificultad(modelo.getTamano());
                            } else if (ans == 1) { // Cerramos pestaña juego
                                dispose();
                            } else {
                                System.out.println("ERROR");
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La clave son 4 colores, ¡rellénalos todos!");
                }
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
