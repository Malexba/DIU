package master;
import java.io.*;

/**
 * Componente de la clase asignatura que indica el curso en el que se está.
 * @author Grupo 1L, equipo 3
 */
public class Curso implements Serializable {
    private int ini;
    private int fin;
    
    /**
     * Constructor de la clase. Recibe el año inicial y gracias a él fija el
     * de finalización.
     * @param ini Año de inicio del curso
     */
    public Curso ( int ini ) {
        this.ini = ini;
        this.fin = ini + 1;
    }
    
    /**
     * Muestra el año de comienzo.
     * @return Año de inicio del curso
     */
    public int getIni () {
        return ini;
    }
    
    /**
     * Muestra el año de finalización.
     * @return Año en el que acaba el curso
     */
    public int getFin () {
        return fin;
    }
}
