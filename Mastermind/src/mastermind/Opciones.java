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
        // Dificultades
        JPanel dificultad = new JPanel(new FlowLayout());
        JLabel nombreD = new JLabel("Dificultad:");
        dificultad.add(nombreD);
        String[] dificultadesList = {"Normal","Fácil","Difícil"};
        SpinnerListModel spinner = new SpinnerListModel(dificultadesList);
        JSpinner dificultades = new JSpinner(spinner);
        dificultad.add(dificultades);
        panelSup.add(dificultad,BorderLayout.NORTH);
        // Música
        JPanel musica = new JPanel(new FlowLayout());
        JLabel nombreM = new JLabel("Música:");
        musica.add(nombreM);
        JCheckBox checkbox = new JCheckBox();
        checkbox.setSelected(true);
        musica.add(checkbox);
        panelSup.add(musica,BorderLayout.CENTER);
        add(panelSup,BorderLayout.NORTH);
        // Muestro el juego por pantalla y asocio vista con modelo
        JButton restablecer = new JButton("Restablecer ajustes por defecto");
        restablecer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                modelo.cambiarDificultad(1); // Restablece dificultad por defecto
                if (!m.sonando())
                    m.iniciarSonidoContinuo();
                sonidoBoton();
            }
        });
        add(restablecer,BorderLayout.CENTER);
        // Configuro botones de la parte inferior
        JButton aplicar = new JButton("Aplicar");
        aplicar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                // Función de comprobación del modelo
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