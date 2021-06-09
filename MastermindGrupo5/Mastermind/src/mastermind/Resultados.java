package mastermind;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import static mastermind.Ventana.w;

/**
 * Ventana para la visualización de resultados.
 * Muestra un top con los resultados guardados de la dificultad actual.
 * @author Grupo 5
 */
public class Resultados extends Ventana implements Observer {
    
    private ModeloJuego modelo;
    
    private JLabel Jnombre[] = new JLabel[10];
    private JLabel Jtiempo[] = new JLabel[10];
    private String nombre[] = new String[10];
    private String tiempo[] = new String[10];
    
    public Resultados (ModeloJuego m) {
        super();
        modelo = m;
        setLayout(new BorderLayout());
        // Tabla de resultados
        JPanel resultados = new JPanel(new GridLayout(11,3));
        resultados.add(letraBien(new JLabel("Puesto")));
        resultados.add(letraBien(new JLabel("Nombre")));
        resultados.add(letraBien(new JLabel("Tiempo")));
        modelo.importar(modelo.getTamano(),nombre,tiempo);
        for (int i=0;i<10;i++) { // Creamos tabla sin resultados
            resultados.add(letraBien(new JLabel(i+1 + ".")));
            Jnombre[i] = letraBien(new JLabel(nombre[i]));
            resultados.add(Jnombre[i]);
            Jtiempo[i] = letraBien(new JLabel(tiempo[i]));
            resultados.add(Jtiempo[i]);
        }
        add(resultados,BorderLayout.CENTER);
        // Selector de dificulad (es un controlador)
        JPanel dificultad = new JPanel(new FlowLayout());
        JLabel nombreD = new JLabel("Dificultad:");
        dificultad.add(nombreD);
        String[] dificultadesList = {"Fácil","Normal","Difícil"};
        SpinnerListModel spinner = new SpinnerListModel(dificultadesList);
        JSpinner dificultades = new JSpinner(spinner);
        ((JSpinner.DefaultEditor) dificultades.getEditor()).getTextField().setEditable(false);
        int difActual;
        if ( modelo.getTamano() == 4 ) {
            difActual = 0;
        } else if ( modelo.getTamano() == 8) {
            difActual = 2;
        } else {
            difActual = 1;
        }
        dificultades.setValue(dificultadesList[difActual]);
        dificultades.addChangeListener(new ChangeListener() { 
            public void stateChanged(ChangeEvent e) {
                if (dificultades.getValue() == "Fácil") {
                    modelo.importar(4,nombre,tiempo);
                } else if (dificultades.getValue() == "Normal") {
                    modelo.importar(6,nombre,tiempo);
                } else {
                    modelo.importar(8,nombre,tiempo);
                }
                sonidoBoton();
            }
        });
        dificultad.add(dificultades);
        add(dificultad,BorderLayout.SOUTH);
        // Configuracion de la ventana
        setTitle("Resultados");
        pack();
        setResizable(false);
        setSize(w/3,h/2);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public JLabel letraBien(JLabel etiqueta) {
        etiqueta.setAlignmentX(Component.CENTER_ALIGNMENT);
        etiqueta.setFont(new Font("Arial", Font.BOLD, Math.round(w/60)));
        return etiqueta;
    }

    @Override
    public void update(Observable o, Object arg) {
        for (int i=0;i<10;i++) {
            Jnombre[i].setText(nombre[i]);
            Jtiempo[i].setText(tiempo[i]);
        }
    }
}
