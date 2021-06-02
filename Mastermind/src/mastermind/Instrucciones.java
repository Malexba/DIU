package mastermind;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Grupo 5
 */
public class Instrucciones extends Ventana {
    
    private Container pane;
    private JButton anterior, siguiente;
    private JPanel panel, panelBotones;
    private CardLayout lo = new CardLayout();
    private int pos;
    
    public Instrucciones() {
        setLayout(lo);
        pos = 1;
        // Creamos botones
        anterior = new JButton("Anterior");
        anterior.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                lo.previous(pane);
                pos--;
                if ( pos == 1 ) {
                    anterior.setEnabled(false);
                }
                if (!siguiente.isEnabled()) {
                    siguiente.setEnabled(true);
                }
            }
        });
        siguiente = new JButton("Siguiente");
        siguiente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                lo.next(pane);
                pos++;
                if(pos == 5) {
                    siguiente.setEnabled(false);
                }
                if (!anterior.isEnabled()) {
                    anterior.setEnabled(true);
                }
            }
        });
        panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(anterior);
        panelBotones.add(siguiente);
        // Gestión del panel
        panel = new JPanel(new BorderLayout());
        panel.add(panelBotones,BorderLayout.SOUTH);
        pane = getContentPane();
        panel.add(new JLabel("<html>En Mastermind tienes que<br>adivinar un código de 4 colores.<br>Arrastra fichas desde su caja o<br>pulsa sobre los huecos para<br>intentar acertar la combinación.<br>Puedes cambiar la dificultad en<br>'Opciones' para elegir el nº de colores.<br>¡A más colores, más difícil de acertar!</html>"),BorderLayout.CENTER);
        pane.add(panel,"1");
        panel.add(new JLabel("<html>Una vez creas que tienes la<br>combinación, mándala.<br>Si aciertas un color te lo ndicaré con una bola blanca;<br>y si además la colocas en la<br>posición correcta, la bola será negra.</html>"),BorderLayout.CENTER);
        pane.add(panel,"2");
        panel.add(new JLabel("Aquí irían instrucciones con colorines (importar imagen)."),BorderLayout.CENTER);
        pane.add(panel,"3");
        panel.add(ejemplo(1),BorderLayout.CENTER);
        pane.add(panel,"4"); // Ejemplo 1 (bola blanca)
        panel.add(ejemplo(2),BorderLayout.CENTER);
        pane.add(panel,"5"); // Ejemplo 2 (bola negra)
        
        // Configuracion de la ventana
        //setLayout(new BorderLayout());
        //add(pane,BorderLayout.CENTER);
        //add(panelBotones,BorderLayout.SOUTH);
        setTitle("Instrucciones");
        setBounds(0,0,300,200);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JPanel ejemplo(int c) {
        // Mostramos la clave a adivinar (tapada)
        JPanel ej = new JPanel(new BorderLayout());
        JPanel tablero = new JPanel();
        tablero.setBackground(new Color(165,100,32));
        tablero.setLayout(new GridLayout(1, 2, 0, 15));
        // Pistas
        JPanel panelPista = new JPanel();
        panelPista.setBackground(new Color(165,100,32));
        panelPista.setLayout(new GridLayout(2,2));
        panelPista.add(new ChinchetaPista(c));
        for (int i = 0; i < 3; i++ ) {
            panelPista.add(new ChinchetaPista());
        }
        // Intento y código
        JPanel panelComb = new JPanel();
        panelComb.setBackground(new Color(165,100,32));
        JPanel codigo = new JPanel();
        codigo.setBackground(new Color(165,100,32));
        codigo.setLayout(new FlowLayout());
        switch (c) {
            case 1:
                for (int j=0; j<4; j++) {
                    panelComb.add(new Chincheta(j+c));
                }
                codigo.add(new Chincheta(8));
                codigo.add(new Chincheta(1));
                codigo.add(new Chincheta(5));
                codigo.add(new Chincheta(6));
                break;
            case 2:
                for (int j=0; j<3; j++) {
                    panelComb.add(new Chincheta(j+c));
                }
                panelComb.add(new Chincheta(1));
                codigo.add(new Chincheta(5));
                codigo.add(new Chincheta(3));
                codigo.add(new Chincheta(6));
                codigo.add(new Chincheta(8));
                break;
        }
        tablero.add(panelPista);
        tablero.add(panelComb);
        ej.add(codigo,BorderLayout.NORTH);
        // Creamos el tablero con el que jugará el usuario
        ej.add(tablero,BorderLayout.CENTER);
        return ej;
    }
}
