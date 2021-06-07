package master;
import java.io.*;

/**
 * Clase que representa a una asignatura cursada por el alumno.
 * @author Grupo 1L, equipo 3
 */
public class Asignatura implements Serializable {
    private final String cod;
    private final String nombre;
    private final String tipo;
    private final int cred;
    private final Curso curso;
    private float nota;
    private boolean presente;
    
    /**
     * Constructor de Asignatura. Por defecto, la nota es -1 (lo cual significa
     * que aún no se ha evaluado) y el alumno consta como no presentado.
     * @param cod Código de la asignatura
     * @param nombre Nombre de la asignatura
     * @param tipo Indica si es obligatoria u optativa
     * @param cred Créditos que da la asignatura al aprobarse
     * @param ini Año de inicio del curso
     */
    public Asignatura ( String cod, String nombre, String tipo, int cred, int ini ) { // -2 es ya evaluado y -1 sin evaluar todavía
        this.cod = cod;
        this.nombre = nombre;
        this.tipo = tipo;
        this.cred = cred;
        this.nota = -1;
        this.curso = new Curso(ini);
        this.presente = false;
    }
    
    /**
     * Muestra el código de la asignatura.
     * @return Código de la asignatura
     */
    public String codigo () {
        return cod;
    }
    
    /**
     * Enseña el nombre de la materia.
     * @return Nombre de la asignatura
     */
    public String mNombre () {
        return nombre;
    }
    
    /**
     * Indica los créditos de la asignatura.
     * @return Créditos de la asignatura
     */
    public int mCred () {
        return cred;
    }
    
    /**
     * Muestra el año de inicio del curso.
     * @return Año de inicio del curso
     */
    public int mAno () {
        return curso.getIni();
    }
    
    /**
     * Muestra el curso.
     * @return Curso en el que se da la asignatura
     */
    public String mCurso () {
        String ano = curso.getIni() + "/" + curso.getFin();
        return ano;
    }
    
    /**
     * Método que establece si el alumno ha acudido o no a la convocatoria.
     * En caso de incomparecencia, se le asigna la nota -1 para indicar que ya
     * ha sido evaluado.
     * @param pres Verdadero en caso de haberse presentado a la asignatura; falso
     * en cualquier otro supuesto
     */
    public void presentacion ( boolean pres ) {
        if ( pres == false ) {
            nota = -1;
        }
        this.presente = pres;
    }
    
    /**
     * Método para calificar una asignatura.
     * @param nota Nota del alumno en esta aignatura
     */
    public void ponerNota ( float nota ) {
        this.nota = nota;
    }
    
    /**
     * Muestra la nota numérica de la asignatura.
     * @return Valor numérico de calificación de la asignatura
     */
    public float getNota () {
        return nota;
    }
    
    /**
     * Método que da la nota de la asignatura. En caso de que el alumno aún
     * no haya sido evaluado o no se presentase a la evaluación, se muestra por
     * pantalla un mensaje indicándolo.
     * @return String con la nota o mensaje correspondiente a la nota numérica
     */
    public String mNota () {
        String eval = "NO PRESENTADO";
        if ( presente ) {
            eval = Float.toString(nota);
        } else if ( nota == -1 ) {
            eval = "NO EVALUADO";
        }
        return eval;
    }
    
    /**
     * Método que indica si el alumno ha superado la asignatura en esta
     * convocatoria.
     * @return Verdadero en caso de haber aprobado; falso en cualquier otro caso
     */
    public boolean aprob () {
        boolean aprob = false;
        if ( presente ) {
            if ( nota >= 5 ) {
                aprob = true;
            }
        }    
        return aprob;
    }
}
