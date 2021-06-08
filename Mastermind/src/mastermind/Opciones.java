package mastermind;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerListModel;

/**
 * Ventana para la gestión de opciones.
 * Permite cambiar la dificultad y gestionar si se quiere que se
 * reproduzca música. Los cambios se aplican una vez lo confirme el usuario.
 * @author Grupo 5
 */
public class Opciones extends Ventana {
    
    private ModeloJuego modelo;
    
    public Opciones(ModeloJuego model, MenuPrincipal m) {
        modelo = model;
        setLayout(new BorderLayout(10,10));
        // Configuro botones de la parte superior
        JPanel panelSup = new JPanel(new BorderLayout());
        // Selector de dificulad
        JPanel dificultad = new JPanel(new FlowLayout());
        JLabel nombreD = new JLabel("Dificultad:");
        dificultad.add(nombreD);
        String[] dificultadesList = {"Fácil","Normal","Difícil"};
        SpinnerListModel spinner = new SpinnerListModel(dificultadesList);
        JSpinner dificultades = new JSpinner(spinner);
        ((DefaultEditor) dificultades.getEditor()).getTextField().setEditable(false);
        int difActual;
        if ( modelo.getTamano() == 4 ) {
            difActual = 0;
        } else if ( modelo.getTamano() == 8) {
            difActual = 2;
        } else {
            difActual = 1;
        }
        dificultades.setValue(dificultadesList[difActual]);
        dificultad.add(dificultades);
        panelSup.add(dificultad,BorderLayout.NORTH);
        // Activación/desactivación de música
        JPanel musica = new JPanel(new FlowLayout());
        JLabel nombreM = new JLabel("Música:");
        musica.add(nombreM);
        JCheckBox checkbox = new JCheckBox();
        checkbox.setSelected(m.sonando());
        musica.add(checkbox);
        panelSup.add(musica,BorderLayout.CENTER);
        JLabel creditos = new JLabel("Royalty Free Music from Bensound", JLabel.CENTER);
        creditos.setForeground(Color.BLUE);
        panelSup.add(creditos,BorderLayout.SOUTH);
        add(panelSup,BorderLayout.NORTH);
        // Muestro el juego por pantalla y asocio vista con modelo
        JButton restablecer = new JButton("Restablecer ajustes por defecto");
        restablecer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                dificultades.setValue(dificultadesList[1]);
                checkbox.setSelected(true);
                sonidoBoton();
            }
        });
        add(restablecer,BorderLayout.CENTER);
        // Configuro botones de la parte inferior
        JButton aplicar = new JButton("Aplicar");
        Opciones self = this;
        aplicar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){ // Aplicamos los cambios al modelo
                if (dificultades.getValue() == "Fácil") {
                    modelo.cambiarDificultad(4);
                } else if (dificultades.getValue() == "Normal") {
                    modelo.cambiarDificultad(6);
                } else {
                    modelo.cambiarDificultad(8);
                }
                if (checkbox.isSelected()) {
                    if (!m.sonando()) {
                        m.iniciarSonidoContinuo();
                    }
                } else{
                    if (m.sonando()) {
                        m.pararSonido();
                    }
                }
                sonidoBoton();
                dispatchEvent(new WindowEvent(self, WindowEvent.WINDOW_CLOSING));
            }
        });
        add(aplicar,BorderLayout.SOUTH);
        // Configuracion de la ventana
        setTitle("Opciones");
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}