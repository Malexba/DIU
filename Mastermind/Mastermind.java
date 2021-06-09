package mastermind;

import javax.swing.SwingUtilities;

/**
 * Clase principal que ejecuta el programa.
 * @author Grupo 5
 */
public class Mastermind {
    public static void main(String[] args) {
       SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               new MenuPrincipal();
            }
        });
    }
}
