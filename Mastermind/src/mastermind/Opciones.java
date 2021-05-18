package mastermind;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerListModel;

/**
 * Clase que representa el controlador del juego.
 * @author Grupo 5
 */
public class Opciones extends Ventana {
    
    private ModeloJuego modelo;
    
    public Opciones(ModeloJuego model, MenuPrincipal m) {
        modelo = model;
        setLayout(new BorderLayout());
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
        dificultades.setValue(dificultadesList[1]);
        dificultad.add(dificultades);
        panelSup.add(dificultad,BorderLayout.NORTH);
        // Activación/desactivación de música
        JPanel musica = new JPanel(new FlowLayout());
        JLabel nombreM = new JLabel("Música:");
        musica.add(nombreM);
        JCheckBox checkbox = new JCheckBox();
        checkbox.setSelected(true);
        musica.add(checkbox);
        panelSup.add(musica,BorderLayout.CENTER);
        panelSup.add(new JLabel("Royalty Free Music from Bensound"),BorderLayout.SOUTH);
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
                reactivar(m);
                sonidoBoton();
                dispose();
            }
        });
        add(aplicar,BorderLayout.SOUTH);
        // Configuracion de la ventana
        setTitle("Opciones");
        pack();
        setResizable(false);
        reactivar(m);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}