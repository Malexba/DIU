package mastermind;

import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author Grupo 5
 */
// Custom button to take input for Mastermind game
public class Chincheta extends JComponent implements MouseListener {
    
    ModeloJuego modelo;
    
    // properties of button
    private int currColor;
    private Color colores[]; // new Color(102,0,153); // Morado
    private boolean mousePressed;
    private boolean editable; // Para poder editar el color al pulsar
    private boolean visible; // Para la clave a adivinar

    // constructor de chinchetas del tablero
    public Chincheta(ModeloJuego m) {
        modelo = m;
        
        this.addMouseListener(this);

        currColor = 0;
        mousePressed = false;
        editable = false;
        visible = true;
        colores = new Color[9];
        colores[0] = Color.BLACK;
        colores[1] = Color.RED;
        colores[2] = Color.CYAN;
        colores[3] = Color.GREEN;
        colores[4] = Color.YELLOW;
        colores[5] = Color.ORANGE;
        colores[6] = Color.BLUE;
        colores[7] = Color.MAGENTA;
        colores[8] = Color.PINK;
    }
    
    // Constructor de chinchetas de la caja y la clave
    public Chincheta(ModeloJuego m, int c) {
        modelo = m;
        currColor = c;
        
        this.addMouseListener(this);
        
        mousePressed = false;
        editable = false;
        visible = true;
        colores = new Color[9];
        colores[0] = Color.BLACK;
        colores[1] = Color.RED;
        colores[2] = Color.CYAN;
        colores[3] = Color.GREEN;
        colores[4] = Color.YELLOW;
        colores[5] = Color.ORANGE;
        colores[6] = Color.BLUE;
        colores[7] = Color.MAGENTA;
        colores[8] = Color.PINK;
    }

    // redraw button
    public void paint(Graphics g) {
        // if mouse was clicked, go to next color
        if (mousePressed) {
            if (currColor == modelo.getTamano()) {
                // and if at end, return to beginning of color list
                currColor = 1;
            } else {
                currColor++;
            }
        }
        // if visible, show color change
        if (visible) {
            g.setColor(colores[currColor]);
        }
        // then redraw
        if (currColor == 0 || !visible) {
            g.drawOval(0,0,30,30);;
        } else {
            g.fillOval(0,0,30,30);
        }
    }

    // get current color
    public int getCurrColor() {
        return currColor;
    }

    //set color and then redraw
    public void setCurrColor(int newColor) {
        currColor = newColor;
        modelo.actualizar();
    }
    
    // Función para el bot´ón borrar, las devuelve a negro
    public void resetearChincheta() {
        currColor = 0;
        modelo.actualizar();
    }

    //set edit state
    public void setEdit(boolean edit) {
        editable = edit;
    }

    // set visible state and redraw
    public void setVis(boolean vis) {
        visible = vis;
        modelo.actualizar();
    }

    // set default size of component
    public Dimension getMinimumSize() {
        return new Dimension(30,30);
    }
    public Dimension getPreferredSize() {
        return new Dimension(30,30);
    }
    
    private class MyDropTargetListener extends DropTargetAdapter {
        
        private DropTarget dropTarget;
        private Chincheta chincheta;
        
        public MyDropTargetListener(Chincheta ch) {
            this.chincheta= ch;
            dropTarget= new DropTarget(ch, DnDConstants.ACTION_COPY, this, true, null);
        }
        
        public void drop(DropTargetDropEvent event) {
            try {
                Transferable tr = event.getTransferable();
                Color color = (Color) tr.getTransferData(TransferableColor.getColorFlavor());
                if(event.isDataFlavorSupported(TransferableColor.getColorFlavor())) {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    chincheta.setBackground(color);
                    event.dropComplete(true);
                    return;
                }
                event.rejectDrop();
            } catch (Exception e) { e.printStackTrace(); event.rejectDrop(); }
        }
    }

    // if mouse pressed and editable, then redraw
    public void mousePressed(MouseEvent e) {
        if (editable) {
            mousePressed = true;
            modelo.actualizar();
        }
    }

    // keep track of mouse being released
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
    }

    // required when implementing mouselistener
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}