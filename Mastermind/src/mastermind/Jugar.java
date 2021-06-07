package mastermind;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Clase que representa el controlador del juego.
 * Permite acceder a una indicación de instrucciones, al reloj y a la caja
 * de fichas. Si no se ha enviado, puede limpiarse la selección de colores
 * del intento actual pulsando el botón borrar.
 * @author Grupo 5
 */
public class Jugar extends Ventana {
    
    private ModeloReloj mReloj;
    private ModeloJuego modelo;
    private VistaJuego tablero;
    private VistaReloj vReloj;
    
    private int ans; // Respuesta a OptionPane
    private boolean intentoValido; // Comprueba que todos los círculos estén rellenos
    private int intento[];
    private JButton fichas, instrucciones, reloj, borrar, comprobar;
    private Chincheta combinacion[][];
    private ChinchetaPista pista[][];
    private ControladorCaja caja;
    private Pista inst;
    
    public Jugar(ModeloJuego model, MenuPrincipal m) {
        modelo = model;
        model.generaClave();
        Jugar self = this;
        setLayout(new BorderLayout());
        // Configuro botones de la parte superior
        JPanel btnPanelSup = new JPanel(new FlowLayout());
        fichas = new JButton("Fichas");
        fichas.setEnabled(false);
        fichas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                fichas.setEnabled(false);
                //caja = new CajaFichas(modelo,self);
                caja=new ControladorCaja(modelo);
                caja.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        fichas.setEnabled(true);
                    }
                });
                sonidoBoton();
            }
        });
        btnPanelSup.add(fichas);
        instrucciones = new JButton("Instrucciones");
        instrucciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                instrucciones.setEnabled(false);
                inst = new Pista();
                inst.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        instrucciones.setEnabled(true);
                    }
                });
                sonidoBoton();
            }
        });
        btnPanelSup.add(instrucciones);
        reloj = new JButton("Reloj");
        reloj.setEnabled(false);
        reloj.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                reloj.setEnabled(false);
                vReloj = new VistaReloj(mReloj);
                mReloj.addObserver(vReloj);
                vReloj.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        reloj.setEnabled(true);
                    }
                });
                sonidoBoton();
            }
        });
        btnPanelSup.add(reloj);
        add(btnPanelSup,BorderLayout.NORTH);
        // Creamos el tablero, muestro el juego por pantalla y asocio vista con modelo
        combinacion = new Chincheta[10][4];
        pista = new ChinchetaPista[10][4];
        tablero = new VistaJuego(modelo, combinacion, pista);
        for (int j=0; j<10; j++) {
            for (int i = 0; i<4; i++) {
                new MyDropTargetListener(combinacion[j][i]);
            }
        }
        modelo.addObserver(tablero);
        add(tablero,BorderLayout.CENTER);
        // Configuro botones de la parte inferior
        JPanel btnPanelInf = new JPanel(new FlowLayout());
        borrar = new JButton("Borrar");
        borrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                for (int j=0; j<4; j++){
                    combinacion[modelo.intentos()][j].resetearChincheta();
                }
                sonidoBoton();
            }
        });
        btnPanelInf.add(borrar);
        comprobar = new JButton("¡Lo tengo!");
        comprobar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                intentoValido = true;
                int j = 0;
                intento = new int[4];
                do {
                    intento[j] = combinacion[modelo.intentos()][j].getCurrColor();
                    if (intento[j] == 0) {
                        intentoValido = false;
                    }
                    j++;
                } while ( ( intentoValido ) && ( j < 4 ) );
                if (intentoValido) {
                    for (j=0; j<4; j++) {
                        combinacion[modelo.intentos()][j].setEdit(false);
                    }
                    if (modelo.compruebaCombinacion(intento, pista[modelo.intentos()])) { // Jugador ha ganado la partida, guardamos su nombre
                        mReloj.parar();
                        String inputValue = JOptionPane.showInputDialog("¡Felicidades, lo has descifrado!\nEscribe aquí el nombre por el\nque quieres que se te recuerde:");
                        // GESTIÓN DE RESULTADOS
                    } else { // Jugador ha perdido un intento, comprobamos que no haya perdido la partida; permitimos edición de siguiente intento
                        if (modelo.intentos() < 0) { // Jugador se ha quedado sin intentos
                            mReloj.parar();
                            ans = JOptionPane.showConfirmDialog(null,"¡Casi lo tenías!\n¿Quieres jugar otra vez?", "Derrota", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                            if ( ans == 0 ) { // Reiniciamos código para adivinar
                                modelo.generaClave();
                                mReloj.resetear();
                            } else if (ans == 1) { // Cerramos pestaña juego
                                cerrarSubventanas();
                                cerrar();
                            } else {
                                System.out.println("ERROR");
                            }
                        } else { // Dejamos que jugador pueda editar las siguientes fichas
                            for (j=0; j<4; j++) {
                                combinacion[modelo.intentos()][j].setEdit(true);
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La clave son 4 colores, ¡rellénalos todos!");
                }
                sonidoBoton();
            }
        });
        btnPanelInf.add(comprobar);
        add(btnPanelInf,BorderLayout.SOUTH);
        // Configuracion de la ventana
        mReloj = new ModeloReloj();
        vReloj = new VistaReloj(mReloj);
        mReloj.addObserver(vReloj);
        vReloj.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                reloj.setEnabled(true);
            }
        });
        caja=new ControladorCaja(modelo);
        caja.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                fichas.setEnabled(true);
            }
        });
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                cerrarSubventanas();
            }
        });
        setTitle("Jugar");
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void cerrarSubventanas() { // Cerrar instrucciones, reloj y fichas
        if (caja != null)
            caja.cerrar();
        if (inst != null)
            inst.cerrar();
        if (vReloj != null)
            vReloj.cerrar();
    }
    
    private class MyDropTargetListener extends DropTargetAdapter{
        
        private DropTarget dropTarget;
        private Chincheta chin;
        
        public MyDropTargetListener(Chincheta chin) {
            this.chin= chin;
            dropTarget= new DropTarget(chin, DnDConstants.ACTION_COPY, this, true, null);
        }
        
        public void drop(DropTargetDropEvent event) {
            try {
                Transferable tr = event.getTransferable();
                int color = (Integer) tr.getTransferData(TransferableColor.getColorFlavor());
                if(event.isDataFlavorSupported(TransferableColor.getColorFlavor())) {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    this.chin.setCurrColor(color);
                    event.dropComplete(true);
                    return;
                }
                event.rejectDrop();
            } catch (Exception e) { e.printStackTrace(); event.rejectDrop(); }
        }
    }

    
}
