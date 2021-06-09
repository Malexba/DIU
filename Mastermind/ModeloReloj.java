package mastermind;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.Observable;
import javax.swing.Timer;

/**
 * Modelo de un reloj.
 * Inicia una cuenta desde 0 en adelante una vez se crea.
 * @author Grupo 5
 */
public class ModeloReloj extends Observable {
    
    private long startTime;
    private long currentTime;
    private final int intervalo= 1000;
    private Timer t;
    private int secondsDisplay, minutesDisplay, hoursDisplay;
    
    public ModeloReloj() {
        startTime = System.currentTimeMillis();
        currentTime = System.currentTimeMillis();
        ActionListener e = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                currentTime = System.currentTimeMillis();
                actualizar();
                setChanged();
                notifyObservers();
            }
        };
        actualizar();
        t = new Timer(intervalo, e);
        t.start();
    }
    
    public void parar() {
        t.stop();
    }
    
    public int segundos() {
        return secondsDisplay;
    }
    
    public int minutos() {
        return minutesDisplay;
    }
    
    public int horas() {
        return hoursDisplay;
    }
    
    public void resetear() {
        startTime = System.currentTimeMillis();
        t.start();
    }
    
    private void actualizar() {
        long elapsedTime = currentTime - startTime;
        long elapsedSeconds = elapsedTime / 1000;
        secondsDisplay = (int) elapsedSeconds % 60;
        long elapsedMinutes = elapsedSeconds / 60;
        minutesDisplay = (int) elapsedMinutes % 60;
        long elapsedHours = elapsedMinutes / 60;
        hoursDisplay = (int) elapsedHours % 60;
    }
}
