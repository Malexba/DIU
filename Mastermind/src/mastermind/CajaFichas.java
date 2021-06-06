package mastermind;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import javax.swing.JFrame;

/**
 * Ventana que muestra al jugador la paleta de fichas disponibles.
 * El usuario puede clickar y arrastrar para colocar el color en el
 * espacio correspondiente de su intento. En función de la dificultad
 * estableciad actualmente, muestra más o menos fichas.
 * @author Grupo 5
 */
public class CajaFichas extends Ventana implements DragGestureListener {
    
    private ModeloJuego modelo;
    
    public CajaFichas(ModeloJuego m, Jugar j) {
        modelo = m;
        DragSource ds[] = new DragSource[modelo.getTamano()];
        Chincheta chin;
        setLayout(new FlowLayout());
        this.getContentPane().setBackground(new Color(165,100,32)); // Marrón
        for (int i = 1; i<=modelo.getTamano(); i++) {
            chin = new Chincheta(i);
            add(chin);
            ds[i-1] = new DragSource();
            ds[i-1].createDefaultDragGestureRecognizer(chin,DnDConstants.ACTION_COPY, j);
        }
        // Configuracion de la ventana
        setTitle("Fichas");
        pack();
        setResizable(false);
        setLocation(w/16,3*h/5);
        setVisible(true);
    }

    @Override
    public void dragGestureRecognized(DragGestureEvent event) {
        Cursor cursor = null;
        Chincheta chin = (Chincheta) event.getComponent();
        Color color = chin.getColor();
        if(event.getDragAction() == DnDConstants.ACTION_COPY) {
            cursor = DragSource.DefaultCopyDrop;
        }
        event.startDrag(cursor, new TransferableColor(color));
    }
}
