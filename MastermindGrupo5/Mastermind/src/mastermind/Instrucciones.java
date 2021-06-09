package mastermind;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static mastermind.Ventana.w;

/**
 * Ventana accesible desde el menú principal con las instrucciones de juego.
 * Las muestra usando un CardLayout no circular en el que se puede avanzar
 * y retroceder en las diversas tarjetas.
 * @author Grupo 5
 */
public class Instrucciones extends Ventana {
    
    private JButton anterior, siguiente;
    private JPanel panel, panelBotones;
    private CardLayout lo = new CardLayout();
    private int pos;
    
    public Instrucciones() {
        setLayout(new BorderLayout());
        pos = 1;
        // Creamos botones
        anterior = new JButton("Anterior");
        anterior.setEnabled(false);
        anterior.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                lo.previous(panel);
                pos--;
                if ( pos == 1 ) {
                    anterior.setEnabled(false);
                }
                if (!siguiente.isEnabled()) {
                    siguiente.setEnabled(true);
                }
                sonidoBoton();
            }
        });
        siguiente = new JButton("Siguiente");
        siguiente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                lo.next(panel);
                pos++;
                if(pos == 5) {
                    siguiente.setEnabled(false);
                }
                if (!anterior.isEnabled()) {
                    anterior.setEnabled(true);
                }
                sonidoBoton();
            }
        });
        panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(anterior);
        panelBotones.add(siguiente);
        add(panelBotones,BorderLayout.SOUTH);
        // Gestión del panel
        panel = new JPanel();
        panel.setLayout(lo);
        panel.setBackground(Color.WHITE);
        JLabel etiqueta1 = new JLabel("<html>En Mastermind tienes que adivinar un código de 4 colores. Arrastra fichas desde su caja o pulsa sobre los huecos para intentar acertar la combinación. Puedes cambiar la dificultad en 'Opciones' para elegir el nº de colores. ¡A más colores, más difícil de acertar!</html>", JLabel.CENTER);
        etiqueta1.setFont(new Font("Arial", Font.BOLD, Math.round(w/75)));
        panel.add(etiqueta1,"1");
        JLabel etiqueta2 = new JLabel("<html>Una vez creas que tienes la combinación, mándala. Si aciertas un color te lo ndicaré con una bola blanca; y si además la colocas en la posición correcta, la bola será negra.</html>", JLabel.CENTER);
        etiqueta2.setFont(new Font("Arial", Font.BOLD, Math.round(w/75)));
        panel.add(etiqueta2,"2");
        panel.add(new JLabel(escalado("../img/Explicacion.png")),"3");
        panel.add(ejemplo(1),"4"); // Ejemplo 1 (bola blanca)
        panel.add(ejemplo(2),"5"); // Ejemplo 2 (bola negra)
        add(panel,BorderLayout.CENTER);
        // Configuracion de la ventana
        setTitle("Instrucciones");
        setBounds(0,0,w/3,w/7);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // Constructor de ejemplos
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
    
    private ImageIcon escalado(String fuente) { // Escalado de imágenes en función del ancho de la consola
        ImageIcon icono = new ImageIcon(getClass().getResource(fuente));
        Image image = icono.getImage(); // Lo transforma
        Image newimg = image.getScaledInstance(w/4, w/12,  java.awt.Image.SCALE_SMOOTH); // Lo escala
        icono = new ImageIcon(newimg);  // Lo destransforma
        return icono;
    }
}
