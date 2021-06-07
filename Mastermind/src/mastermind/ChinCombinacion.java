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
 * Clase que representa a una chincheta genérica de la caja.
 * Permite crear y gestionar las chinchetas de la caja de fichas, desde la
 * que se pueden poner colores para mandar un intento de combinación.
 */
public class ChinCombinacion extends JComponent {
    
    private int  colorActual;
    private Color colors[];
    private boolean mousePressed;
    private boolean editable;
    private boolean visible;
    private Color color;
    private int x,y;

    public ChinCombinacion(int x, int y, Color color) {
        this.x=x;
        this.y=y;
        this.color=color;
        new MyDropTargetListener(this);
        this.addMouseListener(new MouseAdapter(){
            // Si el boton del raton esta presionado y la chincheta es editable, entonces se repinta la componente
            public void mousePressed(MouseEvent e) {
                 if (editable) {
                        mousePressed = true;
                        repaint();
                }
            }
            // 
            public void mouseReleased(MouseEvent e) {
               mousePressed = false;
            }
        });

        colorActual = 0;
        mousePressed = false;
        editable = true;
        visible=true;
        colors = new Color[9];
        colors[1] = Color.RED;
        colors[2] = Color.CYAN;
        colors[3] = Color.GREEN;
        colors[4] = Color.YELLOW;
        colors[5] = Color.PINK;
        colors[6] = Color.BLUE;
        colors[7] = Color.MAGENTA;
        colors[8] = Color.ORANGE;
    }

    // redraw button
    public void paintComponent(Graphics g) {
       // Si el boton del raton ha sido presionado se pinta el siguiente color,           
       if (mousePressed) {
           //si es el último color empieza de nuevo
            if (colorActual == 6) {
                colorActual = 1;
            } else {
                colorActual++;
            }
        }
        // Si es visible muestra el cambio de color
        if(visible)
            g.setColor(colors[colorActual]);
       
        // pinta la chincheta
        if (colorActual == 0 || !visible) {
            g.setColor(color);
            g.fillOval(x,y,30,30);
        } else {
            g.fillOval(x,y,30,30);//
        }
        
    }

    public int getColorActual() {
        return colorActual;
    }
    

    public void setColorActual(int nuevoColor) {
        if (editable)
           colorActual = nuevoColor;
        repaint();
    }

    public void setEdit(boolean edit) {
        editable = edit;
        repaint();
    }

    public void setVis(boolean vis) {
        visible = vis;
        repaint();
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
      private ChinCombinacion chincheta;
      
      public MyDropTargetListener(ChinCombinacion ch) {
           this.chincheta = ch;
           dropTarget = new DropTarget(ch, DnDConstants.ACTION_COPY, this, true, null); }
      
      public void drop(DropTargetDropEvent event) { 
            try { 
                    Transferable tr = event.getTransferable();
                    Integer color = (Integer) tr.getTransferData(TransferableColor.getColorFlavor());
                     if (event.isDataFlavorSupported(TransferableColor.getColorFlavor())) {         
                              event.acceptDrop(DnDConstants.ACTION_COPY); 
                              chincheta.setColorActual(color); 
                              event.dropComplete(true);
                              return; } 
                     event.rejectDrop(); }
             catch (Exception e) { e.printStackTrace(); event.rejectDrop(); }
            }
    }
    
}
