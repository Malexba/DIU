package mastermind;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Jose Vicente Alvarez
 */
public class ControladorCaja extends JFrame implements DragGestureListener {
    
    ModeloJuego modelo;
    
   private ChinCombinacion colors [];
 
   public ControladorCaja(ModeloJuego modelo){
       this.modelo = modelo;
        Toolkit t=Toolkit.getDefaultToolkit();
        Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
        int tamano=(int)(screenSize.getHeight());
        int w=tamano*38/800;
        JPanel colorPanel= new JPanel();
        colors = new ChinCombinacion[this.modelo.getTamano()];
        colorPanel.setLayout(new GridLayout(1,colors.length,5,5));
        colorPanel.setBackground(new Color(155, 103, 60));
        DragSource ds = new DragSource(); 
        
        for (int i=0; i<colors.length; i++) {
            colors[i] = new ChinCombinacion(10,20, Color.WHITE);
            colors[i].setColorActual(i+1);
            colors[i].setEdit(false);
            ds.createDefaultDragGestureRecognizer(colors[i],DnDConstants.ACTION_COPY, this); 
            colorPanel.add(colors[i]);
        }
        add(colorPanel);
        setTitle("Fichas");
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(50*colors.length, 100);
        setLocation(25*w/6,50*w/6);
        this.setVisible(true);   
   }
    
    public void cerrar() { // Función para cerrar esta ventana
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    @Override
     public void dragGestureRecognized(DragGestureEvent event) { 
        Cursor cursor = null;
        ChinCombinacion c = (ChinCombinacion) event.getComponent();
        int color = c.getColorActual();
        if (event.getDragAction() == DnDConstants.ACTION_COPY) { 
              cursor = DragSource.DefaultCopyDrop; } 
        event.startDrag(cursor, new TransferableColor(color)); 
    }
}
