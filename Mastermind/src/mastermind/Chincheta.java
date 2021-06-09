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
 * Clase que representa a una chincheta genérica.
 * Permite definir las chinchetas de intento de combinación, la caja de fichas
 * e incluso los ejemplos de instrucciones.
 * @author Grupo 5
 */

public class Chincheta extends JComponent implements MouseListener {
    
    ModeloJuego modelo;
    
    private int currColor;
    private Color colores[]; // new Color(102,0,153); // Morado
    private boolean mousePressed;
    private boolean editable; // Para poder editar el color al pulsar
    private boolean visible; // Para la clave a adivinar
    private int tam = 30;

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
        colores[5] = Color.PINK;
        colores[6] = Color.BLUE;
        colores[7] = Color.MAGENTA;
        colores[8] = Color.ORANGE;
    }
    
    // Constructor de chinchetas para ejemplos (instrucciones) y para la caja
    public Chincheta(int c) {
        currColor = c;
        tam = 50;
        
        mousePressed = false;
        editable = false;
        visible = true;
        colores = new Color[9];
        colores[0] = Color.BLACK;
        colores[1] = Color.RED;
        colores[2] = Color.CYAN;
        colores[3] = Color.GREEN;
        colores[4] = Color.YELLOW;
        colores[5] = Color.PINK;
        colores[6] = Color.BLUE;
        colores[7] = Color.MAGENTA;
        colores[8] = Color.ORANGE;
    }
    
    // Constructor de chinchetas de la clave
    public Chincheta(ModeloJuego m, int c) {
        modelo = m;
        currColor = c;
        
        mousePressed = false;
        editable = false;
        visible = true;
        colores = new Color[9];
        colores[0] = Color.BLACK;
        colores[1] = Color.RED;
        colores[2] = Color.CYAN;
        colores[3] = Color.GREEN;
        colores[4] = Color.YELLOW;
        colores[5] = Color.PINK;
        colores[6] = Color.BLUE;
        colores[7] = Color.MAGENTA;
        colores[8] = Color.ORANGE;
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
            g.drawOval(0,0,tam,tam);;
        } else {
            g.fillOval(0,0,tam,tam);
        }
    }

    // Color actual (entero)
    public int getCurrColor() {
        return currColor;
    }
    
    // Color actual (color)
    public Color getColor() {
        return colores[currColor];
    }
    
    // Cambiar color (para reinicio)
    public void setColor(int newColor) {
        currColor = newColor;
    }

    // Cambiar color y actualizar
    public void setCurrColor(int newColor) {
        currColor = newColor;
        modelo.actualizar();
    }
    
    // Cambiar color y actualizar (drag and drop)
    public void setCurrColor(Color newColor) {
        if (editable) {
            if (newColor == Color.RED) {
                currColor = 1;
            } else if (newColor == Color.CYAN) {
                currColor = 2;
            } else if (newColor == Color.GREEN) {
                currColor = 3;
            } else if (newColor == Color.YELLOW) {
                currColor = 4;
            } else if (newColor == Color.PINK) {
                currColor = 5;
            } else if (newColor == Color.BLUE) {
                currColor = 6;
            } else if (newColor == Color.MAGENTA) {
                currColor = 7;
            } else if (newColor == Color.ORANGE) {
                currColor = 8;
            }
            modelo.actualizar();
        }
    }
    
    // Función para el botón borrar, las devuelve a negro
    public void resetearChincheta() {
        currColor = 0;
        modelo.actualizar();
    }

    // Habilitar/deshabilitar chincheta
    public void setEdit(boolean edit) {
        editable = edit;
    }

    // Mostrar u ocultar el color de la chincheta
    public void setVis(boolean vis) {
        visible = vis;
    }

    // Dimensiones
    public Dimension getMinimumSize() {
        return new Dimension(tam,tam);
    }
    public Dimension getPreferredSize() {
        return new Dimension(tam,tam);
    }
    
    /*private class MyDropTargetListener extends DropTargetAdapter {
        
        private DropTarget dropTarget;
        private Chincheta chincheta;
        
        public MyDropTargetListener(Chincheta ch) {
            this.chincheta= ch;
            dropTarget= new DropTarget(ch, DnDConstants.ACTION_COPY, this, true, null);
        }
        
        public void drop(DropTargetDropEvent event) {
            try {
                Transferable tr = event.getTransferable();
                int color = (Integer) tr.getTransferData(TransferableColor.getColorFlavor());
                if(event.isDataFlavorSupported(TransferableColor.getColorFlavor())) {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    chincheta.setCurrColor(color);
                    event.dropComplete(true);
                    return;
                }
                event.rejectDrop();
            } catch (Exception e) { e.printStackTrace(); event.rejectDrop(); }
        }
    }*/

    public void mousePressed(MouseEvent e) {
        if (editable) {
            mousePressed = true;
            modelo.actualizar();
        }
    }

    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
    }

    // Resto de métodos a implementar tras la interfaz
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}